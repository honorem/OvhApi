# OvhApi
RxJava Ovh Api wrapper... soon

##Requesting credentials

To request a consumer key to use in your application, you can use the com.github.cambierr.ovhapi.auth.CredentialRequest object this way:

```java
AccessRules accessRules = new AccessRules().addRule("/cloud/*", Method.GET);

CredentialRequest credRqst = CredentialRequest.build("applicationKey", "applicationSecret", accessRules, "http://youwebsite.ovh").toBlocking().single();

System.out.println(credRqst.getValidationUrl());
```

You can now link your CK to a user account using the validation url. You should be able to use 

```java
credRqst.isLinked()
```

one of these days; I'm waiting for OVH'guys to implement something to be able to test the CK.

Finally, you can retrieve your credentials:

```java
Credential creds = credRqst.getCredential();
```
##Using existing credentials

Nothing more simple:
```java
Credential creds = Credential.build("applicationKey", "applicationSecret", "consumerKey").toBlocking().single();
```

Like for the credential request, you should be able to use

```java
creds.check()
```

as soon as this will be implemented in the OVH API.

##Load your project

```java
Project project = Project.byId(creds, "myProjectId").toBlocking().single();
```

or list all your projects:

```java
List<Project> projects = Project.list(creds).toList().toBlocking().single();
```

##List your instances
```java
Region region = Region.byName(creds, "SBG1");

List<Instance> instancesAtSBG1 = Instance.list(creds, region).toList().toBlocking().single();

List<Instance> allInstances = Instance.list(creds, null).toList().toBlocking().single();
```

##Kill your instances
```java
for(Instance instance:allInstances){
    instance.kill().toBlocking().single();
}
```


##Do it again, but asynchronously !
```java
Credential.build("applicationKey", "applicationSecret", "consumerKey")
                .flatMap((Credential t) -> Project.byId(t, "projectId")
                        .flatMap((Project p) -> Instance.list(p, null))
                        .flatMap((Instance i) -> i.kill())
                )
                .subscribe();
```

##Maven
```xml
<dependency>
  <groupId>com.github.cambierr</groupId>
  <artifactId>ovhapi</artifactId>
  <version>...</version>
</dependency>
```