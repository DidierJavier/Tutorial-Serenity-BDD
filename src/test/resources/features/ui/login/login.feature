#language: es
@FeatureName:LoginOrange
Característica: Login de usuario en OrangeHRM

  Como usuario del sistema OrangeHRM
  Quiero poder iniciar sesion con mis credenciales correctas
  Para acceder a las funcionalidades de la aplicacion

  Antecedentes:
    Dado que el usuario esta en la pagina de inicio de sesion

  Escenario: Login exitoso con credenciales validas
    Cuando el usuario ingresa con credenciales validas
    Entonces el usuario deberia ver la pagina principal del sistema

#  Escenario: Login fallido con contraseña incorrecta
#    Cuando el usuario ingresa el nombre de usuario correcto y la contrasena incorrecta
#    Entonces el usuario no puede acceder a la pagina de OrangeHRM
#
#  Escenario: Login fallido con nombre de usuario incorrecto
#    Cuando el usuario ingresa el nombre de usuario incorrecto y la contrasena correcta
#    Entonces el usuario no puede acceder a la pagina de OrangeHRM
