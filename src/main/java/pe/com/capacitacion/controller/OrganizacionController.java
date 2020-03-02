package pe.com.capacitacion.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import pe.com.capacitacion.bean.Organizacion;
import pe.com.capacitacion.dto.ResponseOrgMsg; 
import pe.com.capacitacion.service.OrganizacionService;

/**
 * OrganizacionController
 * @author cguerra
 **/
 @Slf4j      //Autogenerar LOG4J.
 @RestController 
 @RequestMapping( "/organizationservice" ) //NO USAR: [server.servlet.context-path], 'BOOT-ADMIN' reconocera el 'ACTUATOR'.
 @Api( value="OrganizacionController", description="'CONTRATO/API' para la gestion de 'ORGANIZACION'." )
 public class OrganizacionController{
 
		@Autowired
		private OrganizacionService objOrganizacionService;
		
		
	   /**
	    * agregarOrganizacion 	
	    * @param  organizacion
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/
		@PostMapping( "/post/organizaciones" )
	    @ApiOperation( value="Funcionalidad para [CREAR] una ENTIDAD de tipo Organizacion.", nickname="agregarOrganizacion", notes="Funcionalidad para [CREAR] una ENTIDAD de tipo Organizacion." )
		public ResponseEntity<ResponseOrgMsg> agregarOrganizacion( @RequestBody Organizacion organizacion ){ 
			   log.info( "-----> Organizacion 'agregarOrganizacion': {}", organizacion );
 
			   //Ejecutar: 
			   ResponseEntity<ResponseOrgMsg> objResponseOrgMsg = this.objOrganizacionService.agregarOrganizacionService( organizacion ); 
			   return objResponseOrgMsg; 
		}
		
	   /**
	    * eliminarOrganizacion 	
	    * @param  id
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/
		@DeleteMapping( "/delete/organizaciones/{id}" )
	    @ApiOperation( value="Funcionalidad para [ELIMINAR] una ENTIDAD de tipo Organizacion por ID.", nickname="eliminarOrganizacion", notes="Funcionalidad para [ELIMINAR] una ENTIDAD de tipo Organizacion por ID." ) 
		public ResponseEntity<ResponseOrgMsg> eliminarOrganizacion( @PathVariable( "id" ) Long id ){  
			   log.info( "-----> Organizacion 'eliminarOrganizacion': {}", id );
 
			   //Ejecutar: 
			   ResponseEntity<ResponseOrgMsg> objResponseOrgMsg = this.objOrganizacionService.eliminarOrganizacionService( id ); 
			   return objResponseOrgMsg; 
		}
		
	   /**
	    * consultarOrganizacionesAll	
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/ 
		@GetMapping( "/get/organizaciones" )
	    @ApiOperation( value="Funcionalidad para [CONSULTAR] una ENTIDAD de tipo Organizacion completa.", nickname="consultarOrganizacionesAll", notes="Funcionalidad para [CONSULTAR] una ENTIDAD de tipo Organizacion completa." ) 
		public ResponseEntity<ResponseOrgMsg> consultarOrganizacionesAll(){
			   log.info( "-----> Organizacion 'consultarOrganizacionesAll'" );
	 
			   //Ejecutar: 
			   ResponseEntity<ResponseOrgMsg> objResponseOrgMsg = this.objOrganizacionService.consultarOrganizacionesAllService(); 
			   return objResponseOrgMsg; 
		}
	
	   /**
	    * consultarOrganizacionesPorId	
	    * @param  id
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/   
		@GetMapping( "/get/organizaciones/{id}" ) 
	    @ApiOperation( value="Funcionalidad para [CONSULTAR] una ENTIDAD de tipo Organizacion por ID.", nickname="consultarOrganizacionesPorId", notes="Funcionalidad para [CONSULTAR] una ENTIDAD de tipo Organizacion por ID." )
		public ResponseEntity<ResponseOrgMsg> consultarOrganizacionesPorId( @PathVariable( "id" ) Long id ){ 
			   log.info( "-----> Organizacion 'consultarOrganizacionesPorId': id={}", id ); 
 
			   //Ejecutar: 
			   ResponseEntity<ResponseOrgMsg> objResponseOrgMsg = this.objOrganizacionService.consultarOrganizacionesPorIdService( id ); 
			   return objResponseOrgMsg; 
		}
		 
 }
 
