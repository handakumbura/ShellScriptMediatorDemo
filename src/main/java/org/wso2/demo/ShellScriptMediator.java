package org.wso2.demo;

import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellScriptMediator extends AbstractMediator { 
	
    private String scriptname = "/home";//fully qualified name of the script
    private String scriptparam = "/home";//parameter passed to the script
    private Log log = LogFactory.getLog(ShellScriptMediator.class);
        
        
	public boolean mediate(MessageContext context) { 
		
            
            if (log.isDebugEnabled()) 
            {
                log.debug("debug info"); 
            }
                String cmd = "sh "+scriptname+" "+scriptparam;
                ShellExecutor executor = new ShellExecutor(cmd);
                String result = executor.execute();
                log.info("std out :: \n"+result);
		return true;
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
