package config.email;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeThread extends Thread {
	private JProgressBar pbTime;
	
	@Override
	public void run() {
		pbTime.setValue(180);
		pbTime.setString("300");
		// ProgressBar 카운팅 시작
		for(int i=180; i>=0; i--) {
			try {
				int now = pbTime.getValue();
				pbTime.setValue(now - 1);
				pbTime.setString(String.valueOf(now));
				Thread.sleep(1000);
			}catch(Exception e) {
				System.out.println("==========Thread Stop==========");
				e.printStackTrace();
				System.out.println("===============================");
				
			}
		}
		// for문 끝날 시 인증시간 만료 메세지 출력
		JOptionPane.showMessageDialog(null, "인증 시간이 만료되었습니다!");
	}
	
	public void stopThread() {
		super.interrupt();
	}
}
