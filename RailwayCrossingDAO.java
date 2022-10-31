package dao;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import model.RailwayCrossing;

/**** files will be created here. if any update happens, new files will be created with updated details ***/
public class RailwayCrossingDAO {
	int fileCount = 1;
	String railwayCrossingList = "Railway_crossing_list.txt";  
	String rcDetailsForPublic = "RCDetails_for_public";
	String trainDetails = "Train_details";

	public void update() {													//creates a new file when there is an update on files

		railwayCrossingList = "Railway_crossing_list" + fileCount + ".txt";
		rcDetailsForPublic = "RCDetails_for_public" + fileCount;
		trainDetails = "Train_details" + fileCount;
		fileCount++;
	}

	//adds new railway crossing details into database
	public synchronized void addNewCrossing(RailwayCrossing railwayCrossing) throws IOException {
		FileWriter crossingEntry = new FileWriter(railwayCrossingList, true);
		BufferedWriter log = new BufferedWriter(crossingEntry);

		FileWriter printableForUser = new FileWriter(rcDetailsForPublic, true);
		BufferedWriter writePrintableForUser = new BufferedWriter(printableForUser);

		FileReader fileReader = new FileReader(railwayCrossingList);
		BufferedReader bufReader = new BufferedReader(fileReader);

		FileWriter fileWriter = new FileWriter(trainDetails, true);
		BufferedWriter trainDetails = new BufferedWriter(fileWriter);

		log.append(railwayCrossing.isOpened() + ":");
		log.append(railwayCrossing.getName() + ":");
		log.append(railwayCrossing.getAddress().getBuildingNo() + ":" + railwayCrossing.getAddress().getStreet() + ":"
				+ railwayCrossing.getAddress().getCity() + ":");
		log.append(
				railwayCrossing.getInchargeName().getName() + ":" + railwayCrossing.getInchargeName().getPhone() + ":");
		log.append(railwayCrossing.getInchargeName().getEmail());

		writePrintableForUser.append(railwayCrossing.getAddress().getStreet() + ":" + railwayCrossing.getName() + ":"
				+ railwayCrossing.isOpened() + "\n");

		trainDetails.append(railwayCrossing.getAddress().getStreet());

		railwayCrossing.getTimings().forEach((K, V) -> {

			try {
				log.append(":" + K + "=" + V);
				trainDetails.append(":" + K + "=" + V);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		log.newLine();
		trainDetails.newLine();
		log.close();
		writePrintableForUser.close();
		trainDetails.close();
		System.out.println("Added Successfully");
		crossingEntry.close();
		bufReader.close();
		printableForUser.close();

	}

	//fetch and displays the railway crossing details when user requests for it
	//display format --> Location     RailwayCrossingCode	Status
	public void displayData() throws IOException {

		FileReader readRC = new FileReader(rcDetailsForPublic);
		BufferedReader readRCData = new BufferedReader(readRC);

		String out;

		System.out.println("********************  Railway Crossing List  *******************");
		System.out.println("----------------------------------------------------------------");
		System.out.println("Location\t\t\tCode\t\t\tStatus");
		System.out.println("----------------------------------------------------------------");
		while ((out = readRCData.readLine()) != null) {

			String[] components = out.split(":");
			for (String op : components) {
				if (op.equals("false")) {
					op = "Closed";
				} else if (op.equals("true")) {
					op = "Opened";
				}
				System.out.print(op + "\t\t\t");

			}
			System.out.println();

		}
		readRCData.close();
		readRC.close();
	}

	//User can search for the railway crossing status by entering the location name
	public void searchRc(String crossing) throws IOException {
		String out;
		FileReader readRC = new FileReader(rcDetailsForPublic);
		BufferedReader readRCData = new BufferedReader(readRC);

		while ((out = readRCData.readLine()) != null) {
			String[] components = out.split(":");

			if (components[0].equalsIgnoreCase(crossing)) {

				System.out.println("----------------------------------------------------------------");
				System.out.println("Location\t\t\tCode\t\t\tStatus");
				System.out.println("----------------------------------------------------------------");
				for (String op : components) {

					if (op.equalsIgnoreCase("false")) {
						op = "Closed";
					} else if (op.equalsIgnoreCase("true")) {
						op = "Opened";
					}
					System.out.print(op + "\t\t\t");

				}
				break;

			}

		}
		readRCData.close();
		readRC.close();

	}

	//sorts and prints the information based on train timings
	public void sort() throws IOException {
		try {
			FileReader fileReader = new FileReader(railwayCrossingList);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String out = "";
			List<String> statusList = new ArrayList<>();
			List<String> nameList = new ArrayList<>();

			HashMap<Integer, String> beforeSort = new HashMap<>();
			System.out.println("Train Name\t\t\tTime(HHMM)\t\tCrossing Name\t\t\tStatus");
			System.out.println();
			while ((out = bufReader.readLine()) != null) {

				String[] train = out.split(":");
				String statusInBoolean = train[0];
				String status;

				try {
					for (int i = 8; i < train.length; i++) {
						String[] timeAndName = train[i].split("=");
						beforeSort.put(Integer.parseInt(timeAndName[0]), timeAndName[1]);
						if (statusInBoolean.equals("false")) {
							status = "Closed";
							statusList.add(status);
						} else {
							status = "Opened";
							statusList.add(status);
						}
						String name = train[3];
						nameList.add(name);

					}
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}

			}
			
			//TreeMap has been used for sorted output
			TreeMap<Integer, String> sorted = new TreeMap<>(beforeSort);
			Iterator<Integer> itr = sorted.keySet().iterator(); //
			int i = 0;
			while (itr.hasNext()) {

				int key = (int) itr.next();
				System.out.print(
						sorted.get(key) + "\t\t\t" + key + "\t\t\t" + nameList.get(i) + "\t\t\t\t" + statusList.get(i));
				i++;
				System.out.println();
			}

			bufReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {

			System.out.println("File doesn't exits");
		} catch (IOException ioe) {
			System.out.println("Invalid file operation");
		}

	}

	
	//updates should not be done by many users at same time - so multi-threading technique is used here
	//status of a railway crossing can be changed using this method
	public synchronized void updateRc(String crossing, String status) throws IOException { 
		FileReader readRC = new FileReader(railwayCrossingList);
		BufferedReader readRCData = new BufferedReader(readRC);
		String out = "";
		update();
		FileWriter writeRC = new FileWriter(railwayCrossingList, true);
		BufferedWriter writeRCData = new BufferedWriter(writeRC);

		String updatedLine = "";
		while ((out = readRCData.readLine()) != null) {
			String parts[] = out.split(":");
			if (crossing.equalsIgnoreCase(parts[3])) {
				updatedLine = out;
				out = readRCData.readLine();
			}
			writeRCData.append(out);
			writeRCData.newLine();
		}
		String[] updated = updatedLine.split(":");

		if (status.equalsIgnoreCase("Opened")) {
			updated[0] = "true";
		} else if (status.equalsIgnoreCase("Closed")) {
			updated[0] = "false";
		}
		writeRCData.append(updated[0]);
		for (int i = 1; i < updated.length; i++) {
			writeRCData.append(":" + updated[i]);
		}

		readRCData.close();
		writeRCData.close();
		writeRC.close();
		readRC.close();
		FileReader readRC1 = new FileReader(railwayCrossingList);
		BufferedReader readRCData1 = new BufferedReader(readRC1);
		FileWriter writeRcForPublic = new FileWriter(rcDetailsForPublic, true);
		BufferedWriter writeForPublic = new BufferedWriter(writeRcForPublic);
		out = "";
		while ((out = readRCData1.readLine()) != null) {

			String[] parts = out.split(":");
			writeForPublic.append(parts[3] + ":" + parts[1] + ":" + parts[0]);
			writeForPublic.newLine();
		}

		writeForPublic.close();
		writeRcForPublic.close();
		readRCData.close();
		readRC1.close();
		System.out.println("\nUpdated and saved in a new file!!!");
	}

	//deletes and saves the new updated list in a new file
	synchronized public void deleteRc(String crossing) throws IOException {
		FileReader readRC = new FileReader(railwayCrossingList);
		BufferedReader readRCData = new BufferedReader(readRC);

		update();

		FileWriter file1 = new FileWriter(rcDetailsForPublic);
		BufferedWriter buf1 = new BufferedWriter(file1);
		FileWriter file2 = new FileWriter(railwayCrossingList);
		BufferedWriter buf2 = new BufferedWriter(file2);
		FileWriter file3 = new FileWriter(trainDetails);
		BufferedWriter buf3 = new BufferedWriter(file3);

		String out = "";

		while ((out = readRCData.readLine()) != null) {

			String parts[] = out.split(":");

			if (!crossing.equalsIgnoreCase(parts[3])) {
				parts = out.split(":");
				buf1.append(parts[3] + ":" + parts[1] + ":" + parts[0]);
				buf1.newLine();
				buf2.append(out);
				buf2.newLine();
				buf3.append(parts[3]);
				for (int i = 8; i < parts.length; i++) {
					buf3.append(":" + parts[i]);
				}
				buf3.newLine();
			}

		}

		buf3.close();
		buf2.close();
		buf1.close();
		file3.close();
		file2.close();
		file1.close();
		readRCData.close();

		System.out.println("\nOne entry deleted successfully!");

	}

}
