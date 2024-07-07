# 
### Endpoints

#### POST /create
- **Description**: Creates a new member with additional attributes.
- **URL**: `/create`
- **Method**: `POST`
- **Body**:
  ```json
  {
    "otherAttribute": "otherAttribute"
  }
    ```
- **Curl**:
    ```bash
    curl --location 'http://localhost:8083/base/url/api/v1/member/review' \
    --header 'Content-Type: application/json' \
    --data '{
        "otherAttribute": "otherAttribute"
    }'
    ```
- **Response**:
    ```json
      {
        "id": "668a642f319d3d6a0f55142f",
        "otherAttribute": "otherAttribute",
        "workflowStatus": "PENDING_APPROVAL",
        "status": "INACTIVE"
    }
    ```

#### POST /review
- **Description**: Review created member.
- **URL**: `/review`
- **Method**: `POST`
- **Body**:
  ```json
  {
      "id": "668a642f319d3d6a0f55142f"
  }
    ```
- **Curl**:
  ```bash
  curl --location 'http://localhost:8083/base/url/api/v1/member/review' \
    --header 'Content-Type: application/json' \
    --data '{
    "id": "668a642f319d3d6a0f55142f"
    }'
    ```
-  **Response**:
      ```json
        {
          "id": "668a642f319d3d6a0f55142f",
          "otherAttribute": "otherAttribute",
          "workflowStatus": "REVIEWED",
          "status": "INACTIVE"
      }
      ```
  
#### POST /approve
- **Description**: Approve reviewed member.
- **URL**: `/approve`
- **Method**: `POST`
- **Body**:
  ```json
  {
    "id": "668a642f319d3d6a0f55142f"
  }
    ```
- **Curl**:
  ```bash
    curl --location 'http://localhost:8083/base/url/api/v1/member/approve' \
    --header 'Content-Type: application/json' \
    --data '{
      "id": "668a642f319d3d6a0f55142f"
    }'  
  ```
- **Response**:
  ```json
    {
      "id": "668a642f319d3d6a0f55142f",
      "otherAttribute": "otherAttribute",
      "workflowStatus": "APPROVED",
      "status": "INACTIVE"
  }
  ```
  
#### POST /reject
- **Description**: Reject created Member.
- **URL**: `/reject`
- **Method**: `POST`
- **Body**:
  ```json
  {
    "id": "668a642f319d3d6a0f55142f"
  }
    ```
- **Curl**:
  ```bash
    curl --location 'http://localhost:8083/base/url/api/v1/member/reject' \
    --header 'Content-Type: application/json' \
    --data '{
      "id": "668a642f319d3d6a0f55142f"
    }'
    ```
- **Response**:
  ```json
   {
      "id": "668a642f319d3d6a0f55142f",
      "otherAttribute": "otherAttribute",
      "workflowStatus": "REJECTED",
      "status": "INACTIVE"
  }
  ```


#### POST /getById
- **Description**: get member by member ID.
- **URL**: `/getById`
- **Method**: `GET`
- **Body**:
  ```json
  {
    "otherAttribute": "otherAttribute"
  }
    ```
- **Curl**:
  ```bash
  curl --location --request GET 'http://localhost:8083/base/url/api/v1/member/getById' \
    --header 'Content-Type: application/json' \
    --data '{
      "id": "668a642f319d3d6a0f55142f"
    }'  
  ```
- **Response**:
  ```json
    {
      "id": "668a642f319d3d6a0f55142f",
      "otherAttribute": "otherAttribute",
      "workflowStatus": "APPROVED",
      "status": "INACTIVE"
  }
  ```

#### POST /getListOfAuthorizedMembers
- **Description**: Get list of all authorized member.
- **URL**: `/getListOfAuthorizedMembers`
- **Method**: `GET`
- **Curl**:
  ```bash
  curl --location 'http://localhost:8083/base/url/api/v1/member/getListOfAuthorizedMembers'  
  ```
- **Response**:
  ```json
    [
      {
          "id": "668a642f319d3d6a0f55142f",
          "otherAttribute": "otherAttribute",
          "workflowStatus": "APPROVED",
          "status": "ACTIVE"
      }
  ]
  ```
  
#### POST /getListOfUnAuthorizedMembers
- **Description**: Get list of all unauthorized member.
- **URL**: `/getListOfUnAuthorizedMembers`
- **Method**: `GET`
- **Curl**:
  ```bash
  curl --location 'http://localhost:8083/base/url/api/v1/member/getListOfUnAuthorizedMembers'  
  ```
- **Response**:
  ```json
    [
      {
          "id": "668a642f319d3d6a0f55142f",
          "otherAttribute": "otherAttribute",
          "workflowStatus": "APPROVED",
          "status": "INACTIVE"
      }
    ]   
  ```
