package com.tanvir.core.routes;

public class RouteNames {

	public static String BASE_URL = "/base/url/api/v1";
	public static String BASE_URL_V2 = "/base/url/api/v2";


	public final static String MEMBER = "/member";
	public final static String MEMBER_WITH_ID = "/member/{memberId}";
//	public final static String GET_PERSON_BY_ID = PERSON + "/{personId}";
	public final static String CREATE = "/create";
	public final static String REVIEW = "/review";
	public final static String APPROVE = "/approve";
	public final static String REJECT = "/reject";
	public final static String GET_BY_ID = "/getById";
	public final static String GET_ALL_AUTHORIZED = "/getListOfAuthorizedMembers";
	public final static String GET_ALL_UNAUTHORIZED = "/getListOfUnAuthorizedMembers";

    public final static String TURNSTILE = "/turnstile";
    public final static String STATE = "/state";
    public final static String EVENTS = "/events";
    public final static String EVENT_WITH_NAME = "/event/{eventName}";

}
