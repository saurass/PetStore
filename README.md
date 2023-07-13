# Description
* An API built using spring boot to save you Pet pics
* Right now support only for `PNG` and `JPEG` images

# Architecture
* This used formdata request to save you pet pics.
* The request should be multipart form data type. This will ensure that our server doesn't end up consuming it's resources.

# Api Docs

[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/2516148-a3e37669-2d04-4e6f-b1d7-f77dbcd5799b?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D2516148-a3e37669-2d04-4e6f-b1d7-f77dbcd5799b%26entityType%3Dcollection%26workspaceId%3D6569f8c1-ecb8-4fef-a281-3af96858980a)


[<img src="https://blog.haposoft.com/content/images/2021/10/131331002-b2ef5cd3-1e57-4a96-9155-f046d493e823.png" alt="swagger Icon" style="width:130px;"/>](https://htmlpreview.github.io/?https://github.com/saurass/PetStore/blob/master/API%20documentation/index.html)

# Dockerize
* Added docker file for ease of running
* Use the commands as below

```shell
docker build -t petStorePicServ .
docker run -p 9090:9090 petStorePicServ
```