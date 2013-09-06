package com.lftechnology.main;

import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.lftechnology.model.Ticket;
import com.lftechnology.utils.IOUtils;


public class SimpleCache {

	public void start() throws Exception {
		DefaultCacheManager m = new DefaultCacheManager("cluster.xml");
		Cache<Integer, Ticket> cache = m.getCache();
		String command = null; // Line read from standard in
		int ticketid = 1;
		
		IOUtils.dumpWelcomeMessage();
		
		while (true){
			command = IOUtils.readLine("> ");
			if (command.equals("book")) {
				
				String name = IOUtils.readLine("Enter name ");
				String show = IOUtils.readLine("Enter show ");
				
				Ticket ticket = new Ticket(name,show);
				cache.put(ticketid, ticket);
				log("Booked ticket "+ticket);
				ticketid++;
			}
			else if (command.equals("pay")) {
				Integer id = new Integer(IOUtils.readLine("Enter ticketid "));
				Ticket ticket = cache.remove(id);
				log("Checked out ticket "+ticket);
			}
			else if (command.equals("list")) {
				Set <Integer> set = cache.keySet();
				for (Integer ticket: set) {
					System.out.println(cache.get(ticket));
				}
            }
			else if (command.equals("quit")) {
                m.stop();
                break;
			}		
			else {
				log("Unknown command "+command);
				IOUtils.dumpWelcomeMessage();
			}
		}
		
		
		 
		
	}
	
	public static void main(String[] args) throws Exception {
		new SimpleCache().start();

	}
    public static void log(String s){
    	System.out.println(s);
    }
}
