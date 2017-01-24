package messaging;

import ml.HSBCSparkInstance;

import java.util.HashMap;


public class HSBCJabberClientInstance {

	private String botUsername;
	private String botPassword;

	public HSBCJabberClientInstance(HSBCSparkInstance sparkInstance, String _botUserName, String _botPassword) throws Exception
	{
		botUsername = _botUserName;
		botPassword = _botPassword;

		HashMap<String,String> users = new HashMap<String, String>();
		users.put("username", botUsername);
		users.put("password", botPassword);

		//String hostnameJabberServer = "ec2-52-90-48-82.compute-1.amazonaws.com";
		String hostnameJabberServer = "localhost";
		int portJabberClientToServer = 5222;

		XMPPManager xmppManager = new XMPPManager(hostnameJabberServer, portJabberClientToServer);

		xmppManager.init(sparkInstance);
		xmppManager.performLogin(users.get("username"), users.get("password"));
		xmppManager.setStatus(true, "Demandez moi n'importe quoi");

		xmppManager.printRoster();

		boolean isRunning = true;

		while (isRunning) {
			Thread.sleep(50);
		}

		xmppManager.destroy();
	}

}