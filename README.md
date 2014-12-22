**Openbabel Fault Tolerant and Distributed Restful Server With Docker Container**

Use the docker command to pull the docker image from docker-hub

```
$ sudo docker pull yoeluk/giraffe
```

Optionally, you may want to download jut the openbabel image ```sudo docker pull yoeluk/openbabel```. You can run the giraffe image.

```
$ sudo docker run -p 2552:2552 -t yoeluk/giraffe
```

You can put the running process in the background with ```ctr + c```

Clone this repo. ```$ git clone https://github.com/yoeluk/giraffe.git```.

```$ cd giraffe && sbt run```. sbt will ask which main class you would like to run. Choose the frontend option.

Now open a new terminal to test our distributed server with [Httpie](https://pypi.python.org/pypi/httpie). Try this:

```
$ echo '{"mol":"c1ccccc1","inFormat":"smi", "outFormat":"mol"}' | http POST localhost:5000
```

The server's response should look like this:

```
HTTP/1.1 200 OK
Content-Length: 656
Content-Type: application/json; charset=UTF-8
Date: Mon, 22 Dec 2014 13:55:44 GMT
Server: Giraffe REST API

{
    "mol": "\n OpenBabel12221413552D\n\n  6  6  0  0  0  0  0  0  0  0999 V2000\n   -0.8660   -0.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.7321   -0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.7321    1.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -0.8660    1.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -0.0000    1.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  1  6  1  0  0  0  0\n  1  2  2  0  0  0  0\n  2  3  1  0  0  0  0\n  3  4  2  0  0  0  0\n  4  5  1  0  0  0  0\n  5  6  2  0  0  0  0\nM  END\n"
}
```

