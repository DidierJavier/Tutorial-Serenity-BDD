#language: es
@FeatureName:WaysToCreateBodyRequest
Característica: Emplear diferentes formas de envio de Body Request
  Como tester
  Quiero aprender diferentes formas de envìo de Body Request
  Para generar valor en mi trabajo

  #Given: Content type, set cookies, add auth, add param, set headers info, etc...
  #When: get, post, put, delete
  #Then: Validate status code, extract response, extract headers cookies & response body...

  Escenario: Realizar peticion Http con body request usando Hashmap
    Dado que existe un usuario con productos en el carrito de compras usando Hashmap
    Cuando el usuario hace la peticion de agregar los productos al carrito de compras usando Hashmap
    Entonces los productos se agregan al carrito de compras

  Escenario: Realizar peticion Http con body request usando org.json
    Dado que tengo un usuario con con productos en el carrito de compras usando org.json
    Cuando el usuario hace la peticion de agregar los productos al carrito de compras usando org.json
    Entonces los productos se agregan correctamente al carrito usando org.json

  Escenario: Realizar peticion Http con body request usando POJO class
    Dado que tengo un usuario con productos en el carrito de compras usando POJO class
    Cuando el usuario hace la peticion de agregar los productos al carrito de compras usando POJO class
    Entonces los productos se agregan al carrito de compras

  Escenario: Realizar peticion Http con body request usando external json file
    Dado que tengo un usuario con productos en el carrito de compras usando json file
    Cuando el usuario hace la peticion de agregar los productos al carrito de compras usando json file
    Entonces los productos se agregan correctamente al carrito usando org.json