   

IMPORTANTE:
----------
* CONSIDERAR que los DNS, deben estar registrados en el archivo HOST del S.O (Las IPs manejadas deben de ser FIJAS). 
* La 'ARCHIVO DE CONFIGURACION' para el MICROSERVICIO: 'organization-service', se manejara por medio de los SCRIPTs:

  - 1_organization-service_[ConfigMap-Secret].yml
  - 2_organization-service_[Deployment-Service].yml
  
* Dentro del SCRIPT: 'DOCKERFILE' se estan manejando tambien 'VARIABLES DE ENTORNO' para algunos requerimientos en el MICROSERVICIO.  


SWAGGER:
--------
CREAR EL 'CONTRADO/API' ONLINE USANDO:
http://editor.swagger.io/

UNA VEZ DESPLEGADO EL 'MICROSERVICIO' ACCEDER A:   
http://capacitacion.microservicios.organization/swagger-ui.html 


Los LINKs del 'MICROSERVICIO' son:
---------------------------------

  1. Las 'URI' de tipo [GET] son:
     ---------------------------
  
     - consultarorganizacionesAll: 
	   http://capacitacion.microservicios.organization/organizationservice/get/organizaciones
	
     - consultarorganizacionesPorId:   
	   http://capacitacion.microservicios.organization/organizationservice/get/organizaciones/1
 
 
  2. Las 'URI' de tipo [POST] son:
     ----------------------------
     
     - agregarOrganizacion:   
	   http://capacitacion.microservicios.organization/organizationservice/post/organizaciones
 
	   {    
		 "nombre":    "AMAZON", 
		 "direccion": "Calle Chincheros 121, La Molina"   
	   }
 
 
  3. Las 'URI' de tipo [DELETE] son:
     ------------------------------
 
     - eliminarOrganizacion:   
	   http://capacitacion.microservicios.organization/organizationservice/delete/organizaciones/1
	   

DETALLE:
------- 
* Para INFORMACIÓN interna del MICROSERVICIO, apoyarse en ACTUATOR ingresando a: 'http://capacitacion.microservicios.organization/actuator'
* Para acceder a 'PHOMETHEUS' acceder por medio de ACTUATOR asi: 'http://capacitacion.microservicios.organization/actuator/prometheus'

