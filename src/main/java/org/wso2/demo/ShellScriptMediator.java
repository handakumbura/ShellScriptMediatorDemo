package org.wso2.demo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;

public class ShellScriptMediator extends AbstractMediator { 
	
    private String scriptname = "/home";//fully qualified name of the script
    private String scriptparam = "/home";//parameter passed to the script
    private Log log = LogFactory.getLog(ShellScriptMediator.class);
        
        
	public boolean mediate(MessageContext context) { 
		
            
            
                OMElement outputElement;
                String cmd = "sh "+scriptname+" "+scriptparam;
                ShellExecutor executor = new ShellExecutor(cmd);
                String result = executor.execute();
                
                if(validations())
                {
                    throw new SynapseException("validations failed.");
                }    
                if (log.isDebugEnabled()) 
                {             
                    log.debug("script name passed "+scriptname+"\t"+"param passed "+scriptparam);
                    log.debug("result element :: \t"+result);
                }
                log.info(result);
                try {
                    SOAPFactory fac;
                    if(context.isSOAP11())
                    {    
                        fac = OMAbstractFactory.getSOAP11Factory();
                    }
                    else
                    {
                       fac = OMAbstractFactory.getSOAP12Factory();
                    }
                    
                    log.info("--------- \t"+context.getProperty("TESTPROP"));
                    
                    //OMNamespace parentNS = context.getEnvelope().getFirstElement().getNamespace();
                    OMNamespace ns = fac.createOMNamespace("http://services.samples", "ns");
                    OMElement newElement = fac.createOMElement("stdout", ns);
                    newElement.setText(result);
                    context.getEnvelope().buildWithAttachments();
                    
                    //
                    String testXML = "<foo:testelement xmlns:foo=\"http://wso2.com\">test123</foo:testelement>";
                    outputElement = AXIOMUtil.stringToOM(testXML);
                    
                    outputElement.setNamespace(ns);
                    
                    context.getEnvelope().getBody().getFirstElement().addChild(newElement);
                    
                } catch (XMLStreamException ex) {
                    Logger.getLogger(ShellScriptMediator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
              
		return true;
                
	}


    private boolean validations()
    {
        return false;
    }        
        
    /**
     * @return the scriptparam
     */
    public String getScriptparam() {
        return scriptparam;
    }

    /**
     * @param scriptparam the scriptparam to set
     */
    public void setScriptparam(String scriptparam) {
        this.scriptparam = scriptparam;
    }

    /**
     * @return the scriptname
     */
    public String getScriptname() {
        return scriptname;
    }

    /**
     * @param scriptname the scriptname to set
     */
    public void setScriptname(String scriptname) {
        this.scriptname = scriptname;
    }
}
