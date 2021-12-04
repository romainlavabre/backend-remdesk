[BACK](../table.md)

---

# Upload

The upload component permit to upload received document,
he is loaded by request.

### Configure a system

By default, you have this configuration:

```java
import Move;
import Duplication;
import AcceptType;
import Size;
import TransactionSynchronized;


public interface UploadHandler extends EventSubscriber {

    
    @Move( moveRule = MoveS3.class )
    @Duplication( duplicationRule = DuplicationS3.class )
    @AcceptType( types = {
            ContentTypeResolver.APPLICATION_PDF,
            ContentTypeResolver.IMAGE_PNG,
            ContentTypeResolver.IMAGE_JPEG
    } )
    @Size( size = 1250000 )
    @TransactionSynchronized
    String DOCUMENT = "document";

}
``` 

- Call the MoveS3 for definitive upload
- Call the DuplicationS3 for manage the risky duplication
- Accept type:
    - application/pdf
    - image/jpg
    - image/png
- Max size is 1250000 bytes (10 MegaBits)
- The file will be uploaded when transaction will be succeeded


##### Payload

```json
{
    "uploaded_file": {
        "request_parameter_name": {
            "name": "image_name",
            "content-type": "content type",
            "content": "base64",
            "infos": {
                "entity_id": 1
            }
        }
    }
}
```

##### Exception

You can accept all types:

```java
import AcceptType;
@AcceptType(all = true);
```

### Move rule

The move rule must implement:

```java
package MoveRule;
```

### Duplication rule

The duplication rule must implement:

```java
package com.replace.replace.api.upload.Duplication.DuplicationRule;
```

---

[BACK](../table.md)