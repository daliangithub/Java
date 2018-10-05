package com.briup.emdc.test;

import java.util.Collection;
import org.junit.Test;
import com.briup.emdc.bean.Environment;
import com.briup.emdc.client.Client;
import com.briup.emdc.client.Gather;
import com.briup.emdc.server.Server;
import com.briup.emdc.util.Configuration;
import com.briup.emdc.util.ConfigurationImpl;

public class EmdcTest {
	private Configuration configuration = new ConfigurationImpl();
	@Test
	public void client_test() {
		try {
			Gather gather = configuration.getGather();
			Client client = configuration.getClient();
			Collection<Environment> coll = gather.gather();
			client.send(coll);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	@Test
	public void server_test(){
		try {
			Server server = configuration.getServer();
			server.reciver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

