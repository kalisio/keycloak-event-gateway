_[Back to the home page](../README.md)
— Previous page: [5. How to test](./Test.md)
— Next page: [7. Troubleshooting](./Troubleshooting.md)_

--

# 6. Examples of JSON payloads

As found with Keycloak 22.0.5.

## `eventClass: AdminEvent`

Admin events
hold an `operationType`
and a  `resourceType` fields.

e.g. `operationType=CREATE`, and
`resourceType=USER`

### `AdminEvent / CREATE / USER`

```json
{
  "kcVersion":"22.0.5",
  "dateTime":"2024-04-27T16:45:49.308Z",
  "time":1714236349308,
  "realmId":"2b1c9854-ba47-4770-93d0-a00f6ef804e5",
  "realmName":"Kalisio",
  "authDetails" {
    "ipAddress":"172.20.0.1",
    "realmId":"b3936136-db71-4636-9f07-c294a6b49bc8",
    "realmName":"master",
    "clientId":"24cda833-34cd-4094-a839-179f595045bc",
    "userId":"51383fc6-4243-4c1f-afc1-84986e6624c6",
    "username":"admin"
  },
  "eventClass":"AdminEvent",
  "operationType":"CREATE",
  "resourceType":"USER",
  "resourcePath":"users/b3692458-57e8-4593-b36b-077d34ee76a4",
  "error":null,
  "representation":{
    "username":"petitponey",
    "enabled":true,
    "emailVerified":true,
    "firstName":"",
    "lastName":"",
    "email":"petitponey@gmail.com",
    "requiredActions":[],
    "groups":[]
  }
}
```

### `AdminEvent / UPDATE / USER`

```json
{
  "kcVersion":"22.0.5",
  "dateTime":"2024-04-27T20:56:20.216Z",
  "time":1714251380216,
  "realmId":"3892b252-b2ed-4060-bca0-105ff5a7ef3b",
  "realmName":"Kalisio",
  "authDetails":{
    "ipAddress":"172.17.0.1",
    "realmId":"a7ae58a8-f583-4f69-8ea4-f511f555c2ca",
    "realmName":"master",
    "clientId":"83efccb9-f46f-4fee-b767-2fcb3a0f26e7",
    "userId":"72bb6bc3-17f6-4fe2-8e86-82018cef8ba8",
    "username":"admin"
  },
  "eventClass":"AdminEvent",
  "operationType":"UPDATE",
  "resourceType":"USER",
  "resourcePath":"users/96352ea3-d876-48f4-99f5-c3a416759a6a",
  "error":null,
  "representation":{
    "id":"96352ea3-d876-48f4-99f5-c3a416759a6a",
    "createdTimestamp":1714251368388,
    "username":"petitponey",
    "enabled":true,
    "totp":false,
    "emailVerified":true,
    "firstName":"",
    "lastName":"",
    "email":"petiponey1994@gmail.com",
    "attributes":{},
    "disableableCredentialTypes":[],
    "requiredActions":[],
    "notBefore":0,
    "access":{
      "manageGroupMembership":true,
      "view":true,
      "mapRoles":true,
      "impersonate":true,
      "manage":true
    },
    "userProfileMetadata":{
      "attributes":[{
        "name":"username",
        "displayName":"username",
        "required":true,
        "readOnly":true,
        "validators":{}
      },{
        "name":"email",
        "displayName":"email",
        "required":true,
        "readOnly":false,
        "validators":{
          "email":{
            "ignore.empty.value":true
          }
        }
      }],
      "groups":[]
    }
  }
}
```


### `AdminEvent / DELETE / USER`

```json
{
  "kcVersion":"22.0.5",
  "dateTime":"2024-04-27T21:26:07.237Z",
  "time":1714253167237,
  "realmId":"3892b252-b2ed-4060-bca0-105ff5a7ef3b",
  "realmName":"Kalisio",
  "authDetails":{
    "ipAddress":"172.17.0.1",
    "realmId":"a7ae58a8-f583-4f69-8ea4-f511f555c2ca",
    "realmName":"master",
    "clientId":"83efccb9-f46f-4fee-b767-2fcb3a0f26e7",
    "userId":"72bb6bc3-17f6-4fe2-8e86-82018cef8ba8",
    "username":"admin"
  },
  "eventClass":"AdminEvent",
  "operationType":"DELETE",
  "resourceType":"USER",
  "resourcePath":"users/96352ea3-d876-48f4-99f5-c3a416759a6a",
  "error":null,
  "representation":null
}
```

## `eventClass: Event`

Events
hold an `type` field.


e.g. `type = LOGIN `

### `Event / LOGIN`

```json
{
  "kcVersion":"22.0.5",
  "dateTime":"2024-04-27T21:59:36.779Z",
  "time":1714255176779,
  "realmId":"3892b252-b2ed-4060-bca0-105ff5a7ef3b",
  "realmName":"Kalisio",
  "ipAddress":"172.17.0.1",
  "clientId":"account-console",
  "sessionId":"5c5a1e79-3ffb-4699-9ca1-3c3f11a5c8f5",
  "eventClass":"Event",
  "type":"LOGIN",
  "userId":"4c798364-754c-4aab-b6e3-3f17ba7698e2",
  "username":"petitponey",
  "error":null,
  "details":{
    "auth_method":"openid-connect",
    "auth_type":"code",
    "redirect_uri":"http://localhost:8080/realms/Kalisio/account/#/",
    "consent":"no_consent_required",
    "code_id":"5c5a1e79-3ffb-4699-9ca1-3c3f11a5c8f5",
    "username":"petitponey"
  }
}
```

### `Event / LOGOUT`

```json
{
  "kcVersion":"22.0.5",
  "dateTime":"2024-04-27T22:41:15.010Z",
  "time":1714257675010,
  "realmId":"3892b252-b2ed-4060-bca0-105ff5a7ef3b",
  "realmName":"Kalisio",
  "ipAddress":"172.17.0.1",
  "clientId":null,
  "sessionId":"2a68846a-607d-4a1d-90d3-bae5f109b69e",
  "eventClass":"Event",
  "type":"LOGOUT",
  "userId":"4c798364-754c-4aab-b6e3-3f17ba7698e2",
  "username":"petitponey",
  "error":null,
  "details":{
    "redirect_uri":"http://localhost:8080/realms/Kalisio/account/#/"
  }
}
```

--

_[Back to the home page](../README.md)
— Previous page: [5. How to test](./Test.md)
— Next page: [7. Troubleshooting](./Troubleshooting.md)_
