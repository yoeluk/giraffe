**Openbabel Fault Tolerant and Distributed Restful Server With Docker Container**

Use the docker command to pull the docker image from docker-hub

```
$ sudo docker pull yoeluk/giraffe
```

Optionally, you may want to download just the openbabel image ```sudo docker pull yoeluk/openbabel```. You can run the giraffe image.

```
$ sudo docker run -p 2552:2552 -t yoeluk/giraffe
```

You can put the running process in the background with ```ctr + c```

Clone this repo. ```$ git clone https://github.com/yoeluk/giraffe.git```.

```$ cd giraffe && sbt run```. sbt will ask which main class you would like to run. Choose the frontend option.

Now open a new terminal to test our distributed server with [Httpie](https://pypi.python.org/pypi/httpie). Try this:

```
$ echo '{"mol":"c1ccccc1","inFormat":"smi", "outFormat":"inchi"}' | http POST localhost:5000
```

The server's response should look like this:

```
HTTP/1.1 200 OK
Content-Length: 51
Content-Type: application/json; charset=UTF-8
Date: Mon, 22 Dec 2014 15:14:44 GMT
Server: Giraffe REST API

{
    "mol": "InChI=1S/C6H6/c1-2-4-6-5-3-1/h1-6H\n"
}
```

