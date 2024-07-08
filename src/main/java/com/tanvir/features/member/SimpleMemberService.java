package com.tanvir.features.member;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SimpleMemberService {

    private final MemberRepository memberRepository;
    private final UnauthorizedMemberRepository unauthorizedMemberRepository;
    private final ModelMapper modelMapper;

    public Mono<UnauthorizedMember> createMember(UnauthorizedMember member) {
        member.setWorkflowStatus(MemberStates.PENDING_APPROVAL);
        member.setStatus(MemberStates.PENDING_APPROVAL);
        return unauthorizedMemberRepository.save(member);

    }

    public Mono<UnauthorizedMember> reviewMember(String memberId) {
        return unauthorizedMemberRepository.findById(memberId)
            .map(member -> {
                member.setWorkflowStatus(MemberStates.REVIEWED);
                member.setStatus(MemberStates.INACTIVE);
                return member;
            })
            .flatMap(unauthorizedMemberRepository::save);
    }

    public Mono<UnauthorizedMember> rejectMember(String memberId) {
        return unauthorizedMemberRepository.findById(memberId)
            .map(member -> {
                member.setWorkflowStatus(MemberStates.REJECTED);
                member.setStatus(MemberStates.INACTIVE);
                return member;
            })
            .flatMap(unauthorizedMemberRepository::save);
    }

    public Mono<Member> approveMember(String memberId) {
        return unauthorizedMemberRepository.findById(memberId)
            .map(member -> {
                member.setWorkflowStatus(MemberStates.APPROVED);
                member.setStatus(MemberStates.INACTIVE);
                return member;
            })
            .flatMap(unauthorizedMemberRepository::save)
            .flatMap(member -> {
                Member newMember = modelMapper.map(member, Member.class);
                member.setStatus(MemberStates.ACTIVE);
                return memberRepository.save(newMember);
            })
            ;
    }

    public Mono<Member> getById(String memberId) {
        return memberRepository.findById(memberId)
            .switchIfEmpty(unauthorizedMemberRepository.findById(memberId)
                .map(member -> modelMapper.map(member, Member.class))
                .switchIfEmpty(Mono.error(new RuntimeException("Member not found")))
            );
    }

    public Flux<Member> getListOfAuthorizedMembers() {
        return memberRepository.findAll();
    }

    public Flux<UnauthorizedMember> getListOfUnAuthorizedMembers() {
        return unauthorizedMemberRepository.findAll();
    }


}
