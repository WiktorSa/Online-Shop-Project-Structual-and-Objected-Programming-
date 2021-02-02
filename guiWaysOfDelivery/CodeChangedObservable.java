package guiWaysOfDelivery;
import java.util.ArrayList;
import waysofdelivery.Paczkomat;
import client.Observer;
public class CodeChangedObservable implements Subject {
	
	ArrayList<Observer> ObserverList = new ArrayList<Observer>();
	private Paczkomat paczkomat;
	private String nowyKod;
	
	public void registerObserver(Observer observer) 
	{	
		ObserverList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		ObserverList.remove(observer);
	}

	@Override
	public void notifyObservers() 
	{
		for(int i = 0; i<ObserverList.size(); i++) 
			ObserverList.get(i).update(paczkomat);
	}
	
	public void paczkaPrzekierowana(Paczkomat paczkomat,String kod)
	{
		this.paczkomat=paczkomat;
		this.nowyKod=kod;
		paczkomat.setPaczkomatCode(nowyKod);
		notifyObservers();
	}
	
}
