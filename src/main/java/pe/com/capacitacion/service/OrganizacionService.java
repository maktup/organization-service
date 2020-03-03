package pe.com.capacitacion.service;

import java.util.List;  
import com.google.gson.Gson; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; 
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand; 
import lombok.extern.slf4j.Slf4j;
import pe.com.capacitacion.bean.Auditoria;
import pe.com.capacitacion.bean.Departamento;
import pe.com.capacitacion.bean.Organizacion; 
import pe.com.capacitacion.dto.ResponseOrgMsg;
import pe.com.capacitacion.exception.AuditoriaException;
import pe.com.capacitacion.properties.ConfigurationData_01; 
import pe.com.capacitacion.util.Constantes;

/**
 * OrganizacionService
 * @author cguerra
 **/
 @Slf4j      //Autogenerar LOG4J.
 @Service
 public class OrganizacionService extends AuditoriaException{
 
		@Autowired
		private Constantes constantes; 

		@Autowired
		private RestTemplateBuilder objTemplate;  
		
		@Autowired
		private ConfigurationData_01 objConfigurationData01;  //ACCESO: inicia con [grupoconfig01]  
 
        @Autowired
        private DiscoveryClient discoveryClient;
		
        @Autowired
    	private Environment objVariablesEntorno;
        
        @Autowired
        private io.opentracing.Tracer jaegerAlertTracer; 
        
        
	   /**
	    * agregarOrganizacionService 	
	    * @param  organizacion
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/ 
		@HystrixCommand( fallbackMethod = "lanzarExceptionWS" )   //ANTE UNA FALLA LANZARPA EL MÉTODO: [lanzarExceptionWS].
		public ResponseEntity<ResponseOrgMsg> agregarOrganizacionService( Organizacion organizacion ){ 
			   log.info( "------> Organizacion 'agregarOrganizacionService': {}", organizacion );
			   
			   io.opentracing.Scope objJaegerNombreOperacion = this.jaegerAlertTracer.buildSpan( "[agregarOrganizacionService]" ).startActive( true ); 
			   
			   Gson         objGson   = new Gson();
			   String       vURI      = "/organizaciones";
			   RestTemplate objRspTmp = this.objTemplate.build(); 
			   
			   //Variables de Entorno: 
			   this.mostrarVariablesEntorno( this.constantes, this.objConfigurationData01 ); 
			    
			   //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
			   ServiceInstance objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_04 ).get( 0 );
			   
			   String vHostKubernetes = objServiceInstance.getUri() + ""; 
			   log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
			    
			   //Armando URI: 
			   String vURL = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_04 + "/" + Constantes.HTTP_METHOD_02 + vURI); 
			   log.info( "========>: vURL [" + vURL + "]" );
			   
			   //Transformar de OBJETO a JSON:                                         
			   String vParamRequestJSON = objGson.toJson( organizacion );
			   log.info( "========>: vParamRequestJSON: " + vParamRequestJSON ); 
			   	      
			   //Agente JAEGER:  
			   io.opentracing.Span objJaegerServicioHijo_01 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_04 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
			   
			   //Definiendo Entity: 
			   HttpHeaders objHeader = new HttpHeaders(); 
			   objHeader.setContentType( MediaType.APPLICATION_JSON );		 
			   HttpEntity<Object> objEntityRequest = new HttpEntity<Object>( organizacion, objHeader ); 
			   
			   //Enviar mensaje POST: 
			   ResponseEntity<String> vCadenaJSON_01 = objRspTmp.postForEntity( vURL, objEntityRequest, String.class );
			   log.info( "========>: vCadenaJSON_01 [" + vCadenaJSON_01.getBody() + "]" );
			   objJaegerServicioHijo_01.finish();
			   
			   //Transformar de JSON a OBJETO:   
			   pe.com.capacitacion.dto.ResponseOrgMsg objResponseOrgMsg = objGson.fromJson( vCadenaJSON_01.getBody(), pe.com.capacitacion.dto.ResponseOrgMsg.class );
			   log.info( "========>: ResponseOrgMsg: " + objResponseOrgMsg ); 
 
			   //Objeto Return: 
			   ResponseEntity<ResponseOrgMsg> objRetorno = new ResponseEntity<ResponseOrgMsg>( objResponseOrgMsg, HttpStatus.OK ); 
			   
			   objJaegerNombreOperacion.close(); 
			   return objRetorno;	 
		}			
	
	   /**
	    * eliminarOrganizacionService 	
	    * @param  id
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/ 
		@HystrixCommand( fallbackMethod = "lanzarExceptionWS" )   //ANTE UNA FALLA LANZARPA EL MÉTODO: [lanzarExceptionWS].
		public ResponseEntity<ResponseOrgMsg> eliminarOrganizacionService( Long id ){
			   log.info( "------> Organizacion 'eliminarOrganizacionService': {}", id );
	 
			   io.opentracing.Scope objJaegerNombreOperacion = this.jaegerAlertTracer.buildSpan( "[eliminarOrganizacionService]" ).startActive( true ); 
			   
			   String       vURI      = "/organizaciones/";
			   RestTemplate objRspTmp = this.objTemplate.build(); 
			   
			   //Variables de Entorno: 
			   this.mostrarVariablesEntorno( this.constantes, this.objConfigurationData01 ); 
			    
			   //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
			   ServiceInstance objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_04 ).get( 0 );
			   
			   String vHostKubernetes = objServiceInstance.getUri() + ""; 
			   log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
			   			   
			   //Armando URI: 
			   String vURL = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_04 + "/" + Constantes.HTTP_METHOD_03 + vURI + id); 
			   log.info( "========>: vURL [" + vURL + "]" );
			   
			   //Agente JAEGER:  
			   io.opentracing.Span objJaegerServicioHijo_01 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_04 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
			   
			   //Enviar mensaje DELETE: 
			   objRspTmp.delete( vURL );  //Es VOID. 
			   objJaegerServicioHijo_01.finish();
			   
			   //Armando estructura RESPONSE: 
			   Auditoria      objAuditoria      = super.cargarDatosAuditoria( Constantes.IP_APP_NOK, Constantes.MSJ_APP_OK, Constantes.USUARIO_APP_NOK, Constantes.MSJ_APP_OK ); 
			   ResponseOrgMsg objResponseOrgMsg = new ResponseOrgMsg();
			   objResponseOrgMsg.setAuditoria( objAuditoria );
 
			   //Objeto Return: 
			   ResponseEntity<ResponseOrgMsg> objRetorno = new ResponseEntity<ResponseOrgMsg>( objResponseOrgMsg, HttpStatus.OK ); 
			  
			   objJaegerNombreOperacion.close(); 
			   return objRetorno;	 
		}	
		
	   /**
	    * consultarOrganizacionesAllService	
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/ 
		@HystrixCommand( fallbackMethod = "lanzarListaExceptionWS" )   //ANTE UNA FALLA LANZARPA EL MÉTODO: [lanzarListaExceptionWS].
		public ResponseEntity<ResponseOrgMsg> consultarOrganizacionesAllService(){
			   log.info( "------> Organizacion 'consultarOrganizacionesAllService'" );

               io.opentracing.Scope objJaegerNombreOperacion = this.jaegerAlertTracer.buildSpan( "[consultarOrganizacionesAllService]" ).startActive( true ); 
 
			   Gson         objGson   = new Gson();
			   String       vURI_01   = "/organizaciones";
			   String       vURI_02   = "/departamentos-organizacion/";
			   RestTemplate objRspTmp = this.objTemplate.build(); 
			   
			   //Variables de Entorno: 
			   this.mostrarVariablesEntorno( this.constantes, this.objConfigurationData01 ); 
			   
			   //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
			   ServiceInstance objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_04 ).get( 0 );
			   
			   String vHostKubernetes = objServiceInstance.getUri() + ""; 
			   log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
			   
			   //----------------------------------------------------------- [UTL-CAPADB] -----------------------------------------------------------// 
			   //Armando URI:
			   String vURL01 = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_04 + "/" + Constantes.HTTP_METHOD_01 + vURI_01); 
			   log.info( "========>: vURL01 [" + vURL01 + "]" );
		 
			   //Agente JAEGER:  
			   io.opentracing.Span objJaegerServicioHijo_01 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_04 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
			   
			   //Enviar mensaje GET:
			   String vCadenaJSON_01 = objRspTmp.getForObject( vURL01, String.class );
			   log.info( "========>: vCadenaJSON_01 [" + vCadenaJSON_01 + "]" ); 
			   objJaegerServicioHijo_01.finish();
			   
			   //Transformar de JSON a OBJETO: 
			   pe.com.capacitacion.dto.ResponseOrgMsg objResponseOrgMsg = objGson.fromJson( vCadenaJSON_01, pe.com.capacitacion.dto.ResponseOrgMsg.class );
			   log.info( "========>: objResponseOrgMsg: " + objResponseOrgMsg ); 
			   //----------------------------------------------------------- [UTL-CAPADB] -----------------------------------------------------------//
			   
			   
			   List<Organizacion> listaOrganizaciones = objResponseOrgMsg.getListaOrganizaciones();   
			   Organizacion       objOrganizacion     = null;
			   int                idOrg               = 0;
			   
			   //Validacion: 
			   if(  (listaOrganizaciones != null) && (listaOrganizaciones.size() > 0) ){
				   
				    for( int i=0; i<listaOrganizaciones.size(); i++ ){
					     objOrganizacion = listaOrganizaciones.get( i );
					     idOrg           = objOrganizacion.getId().intValue(); 
					   
					     //------------------------------------------------------ [DEPARTMENT-SERVICE] -------------------------------------------------------// 
					     
					     //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
					     objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_01 ).get( 0 );
					   
					     vHostKubernetes = objServiceInstance.getUri() + ""; 
					     log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
					     
					     //Armando URI:
					     String vURL02 = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_01 + "/" + Constantes.HTTP_METHOD_01 + vURI_02 + idOrg); 
					     log.info( "========>: vURL02 [" + vURL02 + "]" );
					   
						 //Agente JAEGER:  
						 io.opentracing.Span objJaegerServicioHijo_02 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_01 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
					     
					     //Enviar mensaje GET:
					     String vCadenaJSON_02 = objRspTmp.getForObject( vURL02, String.class );
					     log.info( "========>: vCadenaJSON_02 [" + vCadenaJSON_02 + "]" ); 
					     objJaegerServicioHijo_02.finish();
					     
					     //Transformar de JSON a OBJETO: 
					     pe.com.capacitacion.dto.ResponseDepMsg objResponseDepMsg = objGson.fromJson( vCadenaJSON_02, pe.com.capacitacion.dto.ResponseDepMsg.class );
					     log.info( "========>: objResponseDepMsg: " + objResponseDepMsg );  
					     //-----------------------------------------------------------------------------------------------------------------------------------//
					   
					     //AGREGANDO:
					     List<Departamento> listaDepartamento = null; 
					   
					     try{
						     listaDepartamento = objResponseDepMsg.getListaDepartamentos(); 
					     }
					     catch( Exception e){ 
					     } 				    
					   
					     if( (listaDepartamento != null) && (listaDepartamento.size() > 0) ){ 
						     log.info( "========>: TAMANIO 'listaDepartamento' [" + listaDepartamento.size() + "]" ); 
						     objOrganizacion.getListaDepartamentos().addAll( listaDepartamento );
					     }
				   } 
			   }			   
			   
			   //Objeto Return:
			   ResponseEntity<ResponseOrgMsg> objRetorno = new ResponseEntity<ResponseOrgMsg>( objResponseOrgMsg, HttpStatus.OK ); 
			   
			   objJaegerNombreOperacion.close(); 
			   return objRetorno;	 
		}	
		
	   /**
	    * consultarOrganizacionesPorIdService	
	    * @param  id
	    * @return ResponseEntity<ResponseOrgMsg>
	    **/ 
		@HystrixCommand( fallbackMethod = "lanzarExceptionWS" )   //ANTE UNA FALLA LANZARPA EL MÉTODO: [lanzarExceptionWS].
		public ResponseEntity<ResponseOrgMsg> consultarOrganizacionesPorIdService( Long id ){
			   log.info( "------> Organizacion 'consultarOrganizacionesPorId': id={}", id ); 
			   
               io.opentracing.Scope objJaegerNombreOperacion = this.jaegerAlertTracer.buildSpan( "[consultarOrganizacionesPorIdService]" ).startActive( true );
			   
			   Gson         objGson   = new Gson();
			   String       vURI_01   = "/organizaciones/";
			   String       vURI_02   = "/departamentos-organizacion/";
			   RestTemplate objRspTmp = this.objTemplate.build(); 
			   
			   //Variables de Entorno: 
			   this.mostrarVariablesEntorno( this.constantes, this.objConfigurationData01 ); 
			   
			   //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
			   ServiceInstance objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_04 ).get( 0 );
			   
			   String vHostKubernetes = objServiceInstance.getUri() + ""; 
			   log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
			   
			   //----------------------------------------------------------- [UTL-CAPADB] -----------------------------------------------------------// 
			   //Armando URI:
			   String vURL01 = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_04 + "/" + Constantes.HTTP_METHOD_01 + vURI_01 + id); 
			   log.info( "========>: vURL01 [" + vURL01 + "]" );
		 
			   //Agente JAEGER:  
			   io.opentracing.Span objJaegerServicioHijo_01 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_04 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
 
			   //Enviar mensaje GET:
			   String vCadenaJSON_01 = objRspTmp.getForObject( vURL01, String.class );
			   log.info( "========>: vCadenaJSON_01 [" + vCadenaJSON_01 + "]" ); 
			   objJaegerServicioHijo_01.finish();
			   
			   //Transformar de JSON a OBJETO: 
			   pe.com.capacitacion.dto.ResponseOrgMsg objResponseOrgMsg = objGson.fromJson( vCadenaJSON_01, pe.com.capacitacion.dto.ResponseOrgMsg.class );
			   log.info( "========>: objResponseOrgMsg: " + objResponseOrgMsg ); 
			   //----------------------------------------------------------- [UTL-CAPADB] -----------------------------------------------------------//
			   
			   
			   List<Organizacion> listaOrganizaciones = objResponseOrgMsg.getListaOrganizaciones();   
			   Organizacion       objOrganizacion     = null;
			   int                idOrg               = 0;
			   
			   //Validacion: 
			   if(  (listaOrganizaciones != null) && (listaOrganizaciones.size() > 0) ){
				   
				    for( int i=0; i<listaOrganizaciones.size(); i++ ){
					     objOrganizacion = listaOrganizaciones.get( i );
					     idOrg           = objOrganizacion.getId().intValue(); 
					   
					     //------------------------------------------------------ [DEPARTMENT-SERVICE] -------------------------------------------------------// 
					     
					     //Obtener el HOST del POD donde está ubicado el 'MICROSERVICIO'. 
					     objServiceInstance = this.discoveryClient.getInstances( Constantes.INSTANCIA_KUBERNETES_01 ).get( 0 );
					   
					     vHostKubernetes = objServiceInstance.getUri() + ""; 
					     log.info( "-----> vHostKubernetes: [" + objServiceInstance.getUri() + "]" );
					     
					     //Armando URI:
					     String vURL02 = (vHostKubernetes + "/" + Constantes.SERVICE_NAME_01 + "/" + Constantes.HTTP_METHOD_01 + vURI_02 + idOrg); 
					     log.info( "========>: vURL02 [" + vURL02 + "]" );
					   
					     //Agente JAEGER:   
					     io.opentracing.Span objJaegerServicioHijo_02 = this.jaegerAlertTracer.buildSpan( "[" + Constantes.INSTANCIA_KUBERNETES_01 + "]" ).asChildOf( objJaegerNombreOperacion.span() ).start();
					     
					     //Enviar mensaje GET:
					     String vCadenaJSON_02 = objRspTmp.getForObject( vURL02, String.class );
					     log.info( "========>: vCadenaJSON_02 [" + vCadenaJSON_02 + "]" ); 
					     objJaegerServicioHijo_02.finish(); 
					     
					     //Transformar de JSON a OBJETO: 
					     pe.com.capacitacion.dto.ResponseDepMsg objResponseDepMsg = objGson.fromJson( vCadenaJSON_02, pe.com.capacitacion.dto.ResponseDepMsg.class );
					     log.info( "========>: objResponseDepMsg: " + objResponseDepMsg );  
					     //-----------------------------------------------------------------------------------------------------------------------------------//
					   
					     //AGREGANDO:
					     List<Departamento> listaDepartamento = null; 
					   
					     try{
						     listaDepartamento = objResponseDepMsg.getListaDepartamentos(); 
					     }
					     catch( Exception e){ 
					     } 				    
					   
					     if( (listaDepartamento != null) && (listaDepartamento.size() > 0) ){ 
						     log.info( "========>: TAMANIO 'listaDepartamento' [" + listaDepartamento.size() + "]" ); 
						     objOrganizacion.getListaDepartamentos().addAll( listaDepartamento );
					     }
				   } 
			   }			   
			   
			   //Objeto Return:
			   ResponseEntity<ResponseOrgMsg> objRetorno = new ResponseEntity<ResponseOrgMsg>( objResponseOrgMsg, HttpStatus.OK ); 
			   
			   objJaegerNombreOperacion.close(); 
			   return objRetorno;	 
		}	
	  
	   /**
	    * mostrarVariablesEntorno
	    * @param constantesParam
	    * @param objConfigurationData01Param 
	    **/
        private void mostrarVariablesEntorno( Constantes constantesParam, ConfigurationData_01 objConfigurationData01Param ){ 
        	    log.info( "-----> Organizacion 'mostrarVariablesEntorno'" );
        	    
			    String vNombreServicio  = constantesParam.nombreServicio; 
			    String vValor_01        = constantesParam.valor01; 
			    String vNombres         = objConfigurationData01Param.getNombres();
			    String vDni             = objConfigurationData01Param.getDni(); 		
 
			    log.info( "vNombreServicio: [" + vNombreServicio + "], vValor_01: [" + vValor_01 + "], vNombres: [" + vNombres + "], vDni: [" + vDni + "]" ); 
			    log.info( "BOOTADMIN_USUARIO: [" + this.objVariablesEntorno.getProperty( "BOOTADMIN_USUARIO" ) + "], BOOTADMIN_PASSWORD: [" + this.objVariablesEntorno.getProperty( "BOOTADMIN_PASSWORD" ) + "]" );    
        }
        
 }
 
