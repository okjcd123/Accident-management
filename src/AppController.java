import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class AppController {
	
	public AppController()
	{
		AppManager.CreateInstance().setAppController(this);
		AppManager.CreateInstance().getAppMain().addActionButtonListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JButton btn = (JButton)arg0.getSource();
						for(int i=0;i<AppManager.CreateInstance().getAppMain().btns.length;i++) {
							if(AppManager.CreateInstance().getAppMain().btns[i]==btn) {
								if(i==0) {
									AppManager.CreateInstance().getAppMain().search();
								}
								else if(i==1) {
									AppManager.CreateInstance().getAppMain().registration();
								}
								else if(i==2) {
									AppManager.CreateInstance().getAppMain().modifyDelete();
								}
								else
								{
									AppManager.CreateInstance().getAppMain().analysis();
								}
							}
						}
					}			
				});
		
		
		
		
	}
}
