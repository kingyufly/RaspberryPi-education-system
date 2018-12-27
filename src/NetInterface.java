import java.net.*;
import java.util.Enumeration;

public class NetInterface {
	private String interfaceName;
	private String ipv4Address;

	public NetInterface(String interfaceName) {
		this.setInterfaceName(interfaceName);
		this.getInterfaceIPAddress();
	}

	public void getInterfaceIPAddress() {
		try {
			Enumeration<?> enumeration = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (enumeration.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) enumeration.nextElement();
				Enumeration<?> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address
							&& netInterface.getName().equals(this.getInterfaceName())) {
						this.setIpv4Address(ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getIpv4Address() {
		return ipv4Address;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setIpv4Address(String ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
}
