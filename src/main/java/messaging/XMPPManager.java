package messaging;

import ml.HSBCSparkInstance;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import java.io.IOException;
import java.util.Collection;


public class XMPPManager {
	private static final int packetReplyTimeout = 500; // millis

	private String server;
	private int port;

	private ConnectionConfiguration config;
	private XMPPConnection connection;

	private ChatManager chatManager;
	private MessageListener messageListener;

	private HSBCSparkInstance sparkInstance;

	public XMPPManager(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public void init(HSBCSparkInstance sparkInstance) throws XMPPException {

		System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));

		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

		config = new ConnectionConfiguration(server, port);
		config.setSASLAuthenticationEnabled(true);
		config.setSecurityMode(SecurityMode.enabled); //allow safety of message sent ?

		connection = new XMPPConnection(config);
		connection.connect();

		System.out.println("Connected: " + connection.isConnected());

		chatManager = connection.getChatManager();
		receiveMessage();

        this.sparkInstance =  sparkInstance;

	}

	public void performLogin(String username, String password) throws XMPPException {
		if (connection!=null && connection.isConnected()) {
			connection.login(username, password);
		}
	}

	public void setStatus(boolean available, String status) {

		Presence.Type type = available? Type.available: Type.unavailable;
		Presence presence = new Presence(type);

		presence.setStatus(status);
		connection.sendPacket(presence);

	}

	public void destroy() {
		if (connection!=null && connection.isConnected()) {
			connection.disconnect();
		}
	}

	public void printRoster() throws Exception {
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			System.out.println(String.format("Buddy:%1$s - Status:%2$s",
					entry.getName(), entry.getStatus()));
			//Status here is only (subscribe / null), tells if the user is waiting a contact to accept his invitation
		}
		//To get user true status (available, unavailable, subscribe, subscribed, unsubscribe, unsubscribed, error)
		//Presence mPresence = roster.getPresence("testuser2@benoit/Smack"); 
		//System.out.println("USer status : " + mPresence.getType());;
	}

	public void sendMessage(String message, String buddyJID) throws XMPPException {
		//System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		chat.sendMessage(message);
	}

	/**
	 * Will receive any message from anyone talking to him
	 * Doesn't need to open a custom chat to hear message
	 */
	public void receiveMessage(){
		chatManager.addChatListener(new ChatManagerListener()
		{
			public void chatCreated(final Chat chat, final boolean createdLocally)
			{
				chat.addMessageListener(new MessageListener()
				{
					public void processMessage(Chat chat, Message message)
					{
						//System.out.println("Received message: " + (message != null && message.getBody() != null ? message.getBody() : "User " + chat.getParticipant() + " is typing"));
						if(message != null && message.getBody() != null)
						{
							try
							{
								//System.out.println(sparkInstance.findResponse(message.getBody()));
                                String response =  sparkInstance.findResponse(message.getBody(), chat.getParticipant());
								sendMessage(response, chat.getParticipant());
								if(sparkInstance.getResponseManager().get(chat.getParticipant()).getCorrect() == null)
									sendMessage("Est-ce que ma réponse vous satisfait ?", chat.getParticipant());


							}
							catch (XMPPException e)
							{
								e.printStackTrace();
							} catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
					}
				});
			}
		});
	}

	public void createEntry(String user, String name) throws Exception {
		//System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));
		Roster roster = connection.getRoster();
		roster.createEntry(user, name, null);
	}

	/**
	 * Sample to listen message from a chat opened (in sample it's chat with testuser2)
	 */
	/*
	class MyMessageListener implements MessageListener {
		public void processMessage(Chat chat, Message message) {
			String from = message.getFrom();
			String body = message.getBody();
			System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
		}
		
	}*/
}