package config.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import config.customlib.CustomSession;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

public class CaptchaConfig {
	public Image getCaptChaImg() {
		// 글씨 폰트 설정
		List<Font> fontList = new ArrayList<>();
		fontList.add(new Font("", Font.HANGING_BASELINE, 40));
		fontList.add(new Font("Courier", Font.ITALIC, 40));
		fontList.add(new Font("", Font.PLAIN, 40));
		
		// 글씨 색상 설정
		List<Color> colorList = new ArrayList<>();
		colorList.add(Color.BLACK);
		
		// 빌더패턴을 이용한 보안문자 생성
		Captcha captcha = new Captcha.Builder(150,110)
				.addText(new NumbersAnswerProducer(6), new DefaultWordRenderer(colorList, fontList))
				.addNoise().addBorder()
				.addBackground(new GradiatedBackgroundProducer())
				.build();
		
		// 세션에 보안문자 저장 후 이미지 반환
		CustomSession session = new CustomSession();
		session.setAttributes("captcha", captcha);
		return captcha.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
	}

}
