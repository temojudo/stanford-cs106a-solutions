import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import acm.util.ErrorException;

public class FacePamphletDataBaseExt implements FacePamphletConstants {

	private HashMap<String, FacePamphletProfileExt> profiles = new HashMap<>();
	private ArrayList<String> usernames = new ArrayList<>();
	private ArrayList<String> passwords = new ArrayList<>();

//	read file and save information in hashmap and arraylists	
	public FacePamphletDataBaseExt(String filename) {
		profiles.clear();
		usernames.clear();
		passwords.clear();

		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null || line.equals(""))
					break;

				StringTokenizer tk = new StringTokenizer(line, "-");
				String name = tk.nextToken();
				String username = tk.nextToken();
				String password = tk.nextToken();

				usernames.add(username);
				passwords.add(password);
				FacePamphletProfileExt profile = new FacePamphletProfileExt(name, username, password);

				profile.setImage(tk.nextToken());
				profile.setStatus(tk.nextToken());

				while (tk.hasMoreTokens())
					profile.addFriend(tk.nextToken());

				profiles.put(name, profile);
			}
			rd.close();
		} catch (Exception e) {
			throw new ErrorException(e);
		}

	}

//	find profile with username and password	
	public FacePamphletProfileExt findProfile(String username, String password) {
		for (String name : profiles.keySet())
			if (profiles.get(name).getUsername().equals(username) && profiles.get(name).getPassword().equals(password))
				return profiles.get(name);

		return null;
	}

//	find profile with profile's name	
	public FacePamphletProfileExt lookupProfile(String name) {
		if (containsProfileName(name))
			return profiles.get(name);

		return null;
	}

//	find profiles with part of name	
	public Iterator<String> lookupProfilesWithKey(String key, String name) {
		String key1, key2;
		if (key.contains(" ")) {
			key1 = key.substring(0, key.indexOf(' '));
			key2 = key.substring(key.indexOf(' ') + 1);
		} else {
			key1 = key;
			key2 = key;
		}

		ArrayList<String> profile = new ArrayList<>();

		for (String profileName : profiles.keySet())
			if (!profileName.equals(name) && (profileName.toUpperCase().contains(key1.toUpperCase())
					|| profileName.toUpperCase().contains(key2.toUpperCase())))
				profile.add(profileName);

		return profile.iterator();
	}

//	add profiles in lists and map	
	public void addProfile(FacePamphletProfileExt profile) {
		profiles.put(profile.getName(), profile);
		usernames.add(profile.getUsername());
		passwords.add(profile.getPassword());
	}

//	delete profile from map	
	public void deleteProfile(FacePamphletProfileExt profile) {
		if (profiles.containsValue(profile))
			profiles.remove(profile.getName());
	}

//	check if map contains name	
	public boolean containsProfileName(String name) {
		if (profiles.containsKey(name))
			return true;

		return false;
	}

//	check if list contains username	
	public boolean containsUsername(String username) {
		if (usernames.contains(username))
			return true;

		return false;
	}

//	check if list contains password	
	public boolean containsPassword(String password) {
		if (passwords.contains(password))
			return true;

		return false;
	}

//	print map in file and save information about profiles	
	public void update() {
		try {
			PrintWriter wr = new PrintWriter(new FileWriter("profiles.txt"));

			for (String name : profiles.keySet()) {
				String line = "";
				FacePamphletProfileExt profile = profiles.get(name);
				line += profile.getName() + "-" + profile.getUsername() + "-" + profile.getPassword() + "-"
						+ profile.getImage() + "-" + profile.getStatus();
				Iterator<String> friends = profile.getFriends();
				while (friends.hasNext()) {
					line += "-" + friends.next();
				}

				wr.println(line);
			}
			wr.close();
		} catch (Exception e) {
			throw new ErrorException(e);
		}
	}

}
