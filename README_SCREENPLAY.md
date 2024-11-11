# Configuraciones archivo "serenity.properties"
chrome.switches=--remote-allow-origins=*;--disable-popup-blocking:
--remote-allow-origins=*: Este interruptor permite que Chrome acepte conexiones desde cualquier origen remoto. Es útil en entornos de prueba donde necesitas evitar restricciones de origen cruzado.
--disable-popup-blocking: Desactiva el bloqueo de ventanas emergentes en Chrome. Esto es útil en pruebas automatizadas donde necesitas que las ventanas emergentes sean permitidas para verificar su contenido o comportamiento.

serenity.console.colors=true:
Esta configuración habilita el uso de colores en la salida de la consola. Los colores pueden ayudar a hacer que los logs y mensajes sean más fáciles de leer y diferenciar.

#serenity.take.screenshots=FOR_FAILURES:
Esta línea está comentada (eliminada de la configuración efectiva). Si estuviera activa, Serenity tomaría capturas de pantalla solo en caso de fallos durante las pruebas.

serenity.take.screenshots=FOR_EACH_ACTION:
Esta configuración indica a Serenity que tome capturas de pantalla para cada acción realizada durante la prueba. Es útil para obtener un registro visual detallado de cada paso de la prueba.

serenity.test.root=${user.dir}${file.separator}src${file.separator}test:
Define el directorio raíz de los tests. Utiliza las variables del sistema (user.dir y file.separator) para construir la ruta completa. user.dir es el directorio de trabajo actual y file.separator es el separador de directorios específico del sistema operativo (por ejemplo, / en Unix o \ en Windows).

serenity.outputDirectory=target${file.separator}site${file.separator}reports:
Especifica el directorio de salida donde Serenity generará los reportes de las pruebas. En este caso, será target/site/reports.

serenity.report.test.durations=false:
Desactiva el reporte de duraciones de las pruebas en los reportes generados por Serenity. Esto puede ayudar a simplificar los reportes si no necesitas los tiempos de ejecución.

serenity.browser.maximized=true:
Indica que el navegador debe abrirse en modo maximizado. Esto es útil para asegurarse de que todos los elementos de la página web sean visibles y accesibles durante las pruebas.

# Archivo built.gradle
defaultTasks 'clean', 'test', 'aggregate'
Define las tareas predeterminadas que se ejecutan cuando simplemente ejecutas gradle sin especificar ninguna tarea. Aquí, se limpiará el proyecto, se ejecutarán las pruebas y se agregará el reporte de Serenity.

repositories { mavenCentral() mavenLocal() }
Especifica los repositorios donde Gradle buscará las dependencias. mavenCentral() es el repositorio central de Maven y mavenLocal() busca en el repositorio local del usuario.

buildscript { repositories { maven { url "https://plugins.gradle.org/m2/" } } dependencies { classpath "net.serenity-bdd:serenity-gradle-plugin:4.2.6" classpath "net.serenity-bdd:serenity-single-page-report:4.2.6" } }
Define los repositorios y las dependencias para el script de compilación de Gradle. Aquí se añaden los plugins de Serenity.

apply plugin: 'java' apply plugin: 'eclipse' apply plugin: 'idea' apply plugin: "net.serenity-bdd.serenity-gradle-plugin"
Aplica los plugins de Gradle necesarios para el proyecto. java es esencial para compilar y ejecutar el código Java, eclipse y idea son para la integración con IDEs Eclipse e IntelliJ IDEA respectivamente, y net.serenity-bdd.serenity-gradle-plugin es para integrar Serenity BDD.

sourceCompatibility = 17 targetCompatibility = 17
Define las versiones de Java que se usarán para compilar el código fuente y el código de destino. Aquí se especifica Java 17.

ext { log4jVersion = '2.22.0' serenityVersion = '4.2.6' restAssuredVersion = '5.0.1' junitVersion = '5.9.3' logbackVersion = '1.5.6' lombokVersion = '1.18.24' javaFakerVersion = '1.0.2' commonsConfigVersion = '2.11.0' mongoDriverVersion = '3.12.11' zip4jVersion = '2.11.5' gcloudStorageVersion = '2.44.1' guavaVersion = '32.1.2-jre' apachePoiVersion = '5.2.5' dom4jVersion = '2.1.4' apacheXmlBeans = '5.2.0' }
Define variables de extensión que almacenan las versiones de las dependencias para facilitar su gestión.

dependencies { implementation "net.serenity-bdd:serenity-core:${serenityVersion}", "io.rest-assured:rest-assured:${restAssuredVersion}", "net.serenity-bdd:serenity-junit5:${serenityVersion}", "net.serenity-bdd:serenity-cucumber:${serenityVersion}", "net.serenity-bdd:serenity-screenplay:${serenityVersion}", "net.serenity-bdd:serenity-screenplay-rest:${serenityVersion}", "net.serenity-bdd:serenity-ensure:${serenityVersion}", "org.junit.jupiter:junit-jupiter-api:${junitVersion}", "org.junit.jupiter:junit-jupiter-engine:${junitVersion}", "org.projectlombok:lombok:${lombokVersion}", "ch.qos.logback:logback-classic:${logbackVersion}", "com.github.javafaker:javafaker:${javaFakerVersion}", "org.apache.commons:commons-configuration2:${commonsConfigVersion}", "org.apache.logging.log4j:log4j-core:${log4jVersion}", "org.mongodb:mongo-java-driver:${mongoDriverVersion}", "net.lingala.zip4j:zip4j:${zip4jVersion}", "com.google.cloud:google-cloud-storage:${gcloudStorageVersion}", "com.google.guava:guava:${guavaVersion}", "org.apache.poi:poi:${apachePoiVersion}", "org.apache.poi:poi-ooxml:${apachePoiVersion}", "org.apache.poi:poi-scratchpad:${apachePoiVersion}", "org.apache.poi:poi-ooxml-lite:${apachePoiVersion}", "org.apache.xmlbeans:xmlbeans:${apacheXmlBeans}", "org.dom4j:dom4j:${dom4jVersion}" testImplementation 'org.junit.vintage:junit-vintage-engine:5.11.1' compileOnly "org.projectlombok:lombok:${lombokVersion}" annotationProcessor "org.projectlombok:lombok:${lombokVersion}" }
Define las dependencias necesarias para el proyecto. Utiliza las versiones definidas en las variables de extensión. Incluye dependencias para Serenity BDD, Rest Assured, JUnit 5, y varias otras bibliotecas.

serenity {reports = ["single-page-html"]}
Configura Serenity BDD para generar un reporte HTML de una sola página.

gradle.startParameter.continueOnFailure = true
Configura Gradle para continuar ejecutando tareas incluso si alguna falla. Esto es útil para no detener el flujo de trabajo completo debido a un solo error.

test {
dependsOn clearReports
useJUnitPlatform()
systemProperty 'environment', System.properties['environment']
systemProperty "file.encoding", "utf-8"
}
test.finalizedBy(aggregate)
Define que la tarea test depende de clearReports, lo que asegura que los reportes previos sean limpiados antes de ejecutar nuevas pruebas.
Usa la plataforma JUnit para ejecutar las pruebas.
Establece propiedades del sistema necesarias para la ejecución de pruebas.
Asegura que la tarea aggregate se ejecute al finalizar test, lo cual es una tarea de Serenity para generar reportes.

Nota: Algunas dependencias no se utilizan por el momento y se tienen para futuros proyectos

# Árbol del proyecto
El proyecto se llama "TutorialSerenityBDD", el cual implementa el patrón de diseño Screenplay y facilita la escritura de código de manera escalable y mantenible. Para una explicación detallada, revisar la documentación sobre el patrón de diseño Screenplay: https://serenity-bdd.github.io/docs/screenplay/screenplay_fundamentals

La carpeta principal es la carpeta "src", la cual contiene las carpetas "main" y "test".
Dentro de la carpeta "test" está el directorio "resources" que a su vez contiene el directorio "features".
Dentro del directorio "features", están los features con los que vamos a trabajar. Para nuestro ejemplo, tenemos el feature llamado "login.feature", ya que vamos a hacer pruebas automatizadas en la página de OrangeHRM; dentro de esta feature vamos a realizar el escenario de login exitoso y login no exitoso.

En resources también está el archivo "serenity.conf" que configura diversos aspectos importantes del entorno de pruebas:
Navegadores: Define las capacidades y opciones para Chrome y Firefox.
Reportes: Configura cómo y cuándo se generan los reportes agregados.
Entornos: Permite seleccionar configuraciones específicas según el entorno de ejecución (como qa).

Finalmente también en resorces está el archivo logback. Este archivo configura Logback para que:
Todos los mensajes de log de nivel INFO o superior se envíen a la consola.
Los mensajes sigan un formato específico, mostrando la hora, el hilo, el nivel de log, el nombre del logger y el mensaje.
Establece el nivel de log INFO para las librerías de Serenity y Cucumber, así como para el logger raíz, filtrando los mensajes de nivel DEBUG.

Dentro de la carpeta test, tenemos las clases necesarias para correr el test, integrando los archivos .feature que corresponden a la capa de negocio con las clases "StepDefinition" que integran la capa de negocio con la capa lógica y donde el actor realiza tareas para cumplir lo que dicta la capa de negocio (.feature).
Se distinguen tres carpetas que son:
1. hooks: Donde se encuentran las clases:
a. "BusinessConfigurationHook"; allí se hace la Configuración de Actores: Esta clase configura un actor que representará al usuario en las pruebas automatizadas utilizando el patrón Screenplay de Serenity BDD.
Hooks y Parametrización: Utiliza hooks de Cucumber para inicializar actores antes de que los escenarios de prueba se ejecuten, y define parámetros personalizados para utilizar en las expresiones de Gherkin.
b. UserInterfaceHook: Configuración del Entorno para Escenarios Específicos. Esta clase está diseñada para configurar las variables de entorno necesarias para los escenarios que tienen la etiqueta @FeatureName:LoginOrange.
Inicialización y Recuerdo de Variables: Guarda variables cruciales (URL, nombre de usuario y contraseña) en el contexto del actor para su uso durante las pruebas.
2. stepDefinitions: Aquí están las clases que convierten el lenguaje Gherkin de los archivos .feature (capa de negocio), a la capa lógica, usando para ello el actor y las tareas requeridas para cumplir el propósito de cada feature y cada scenario.
3. En el paquete "runners", están las clases que van a permitir ejecutar las features y escenarios.

Dentro de la carpeta "main" están los paquetes requeridos para trabajar con el patrón de diseño de automatización de pruebas. Para más información, revisar la documentación en https://serenity-bdd.github.io/docs/screenplay/screenplay_fundamentals


