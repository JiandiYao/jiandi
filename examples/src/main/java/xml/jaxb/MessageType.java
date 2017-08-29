package xml.jaxb;

public class MessageType {

	public static final String GET_VALUE_REQUEST = "getreq";
	public static final String PUT_VALUE_REQUEST = "putreq";
	public static final String DELETE_VALULE_REQUEST = "delreq";
	public static final String READY = "ready";
	public static final String ABORT = "abort";
	public static final String DECISIONS = "commit/abort";
	public static final String ACKNOWLEDGEMENT = "ack";
	public static final String REGISTER = "register";
	public static final String REGISTERATION_ACK = "resp";
	public static final String IGNORE_NEXT = "ignoreNext";
	public static final String IGNORE_NEXT_ACK = "resp";
	public static final String RESPONSE = "resp";
	
	public static final String SUCCESS = "Success";
	public static final String UNSUCCESS = "Error Message";
	
	
	public static final String NETWORK_ERROR_SEND = "Network Error: Could not send data";
	public static final String NETWORK_ERROR_RECEIVE = "Network Error: Could not receive data";
	public static final String NETWORK_ERROR_CONNECT = "Network Error: Could not connet";
	public static final String NETWORK_ERROR_CREATE = "Network Error: Could not create socket";
	
	public static final String XML_ERROR = "XML Error: Received unparseable message";
	public static final String OVERSIZED_KEY = "Oversized key";
	public static final String OVERSIZED_VALUE = "Oversized value";
	public static final String IO_ERROR = "IO Error";
	public static final String DOES_NOT_EXIST = "Does not exist";
	public static final String UNKNOWN_ERROR = "Unknown Error: error-description";

}
