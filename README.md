**Reactive java demo app**

*Prerequisites*

JAVA 11+

*Approach*

Very simple JAVA RESTful application trying to follow reactive approach. For personal interest 
for method authorization in async application also basic auth added. As mentioned in task desciption
in email - permission inheritance is ignored.

**Flow example**

GET all users permissions
curl --user admin:password -X GET http://localhost:8080/users/b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad/permissions
```javascript
[
   {
      "id":"fe258c95-d402-4ad4-9568-e3e463940818",
      "permissionCode":"VIEW",
      "departmentId":"57633250-8e65-4b2a-b814-5838a1b8d3ff",
      "userId":"b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad"
   }
]
```

GET all users permissions for specific department
curl --user admin:password -X GET http://localhost:8080/users/b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad/permissions?departmentId=57633250-8e65-4b2a-b814-5838a1b8d3ff
```javascript
[
   {
      "id":"fe258c95-d402-4ad4-9568-e3e463940818",
      "permissionCode":"VIEW",
      "departmentId":"57633250-8e65-4b2a-b814-5838a1b8d3ff",
      "userId":"b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad"
   }
]
```
GET information about specific permission
curl --user admin:password -X GET http://localhost:8080/users/b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad/permissions/fe258c95-d402-4ad4-9568-e3e463940818
```javascript
{
   "id":"fe258c95-d402-4ad4-9568-e3e463940818",
   "permissionCode":"VIEW",
   "departmentId":"57633250-8e65-4b2a-b814-5838a1b8d3ff",
   "userId":"b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad"
}
```
Revoke specific permission
curl --user admin:password -X DELETE http://localhost:8080/users/b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad/permissions/fe258c95-d402-4ad4-9568-e3e463940818

create a new permission for user
curl --user admin:password -d '{"permissionCode":"VIEW", "departmentId":"5a0bbdbe-c872-4467-9070-b5284d7b658f"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users/b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad/permissions 
```javascript
{
   "id":"b526409e-c3ae-4337-ac36-f6ce6ff6050f",
   "permissionCode":"VIEW",
   "departmentId":"5a0bbdbe-c872-4467-9070-b5284d7b658f",
   "userId":"b05beb44-b7f9-4ec8-ba41-5d0f5844a4ad"
}
```

**Running the application**
clone from https://github.com/narism/reactive-demo.git


In project root

*./gradlew clean build*

navigate to flight-web/build/libs

java -jar flight-web.jar

application should be running in localhost:8080

NB! deployment takes about 30 sec as the graph is built in memory


