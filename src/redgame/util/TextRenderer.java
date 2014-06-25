package redgame.util;
/*
 * TextRenderer.java ���ߣ�Ҧ����
 */


import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.font.*;
/**
 * TextRenderer���Ǹ�ʽ������Ⱦ��
 * ��ʽ:<code>\n  ����</code><br>
 *      <code>\tb ����</code><br>   
 *      <code>\ti б��</code><br>   
 *      <code>\tB ȡ������</code><br>   
 *      <code>\tI ȡ��б��</code><br>   
 *      <code>\tc######## ��ɫ</code><br>   
 * 
 * @author Ҧ����
 */

public class TextRenderer {
    //��ǰ����λ��
    private int m_curx;
    private int m_cury;
    //��ǰ��ɫ
    private Color m_color;
    //��ǰ����
    private Font m_font;
    //����������
    private FontRenderContext m_frc;
    //���ֻ���
    private String ls;
    //һ�еĸ߶�
    private int lh;

    private static int getNumEndPos(String s, int i){
       while (i < s.length() && s.charAt(i) <= '9' && s.charAt(i) >= '0'){
           i++;
       }
       return i;
    }

    private void drawIt(Graphics2D g2d){
        if (ls.equals("")) return; 
        g2d.setFont(m_font);
        g2d.setColor(m_color);

        int i = 0;
        while (ls.charAt(i++) == ' ') 
            m_curx += m_font.getSize() / 2; 

        TextLayout layout = new TextLayout(ls, m_font, m_frc);
        Rectangle2D bounds = layout.getBounds();
        g2d.drawString(ls, m_curx - (int)bounds.getX(), 
                           m_cury - (int)bounds.getY());    
        i = ls.length() - 1;
        while (ls.charAt(i--) == ' ') 
            m_curx += m_font.getSize() / 2; 

        if (lh < bounds.getHeight()) lh = (int)bounds.getHeight();
        m_curx += (int) bounds.getWidth(); 
        ls = "";        
    }
    
    public Dimension drawText(int x, int y, Graphics2D g2d, String s, Image emotion){
        //������ռ�Ĵ�С
        int maxw = 0;
        int maxh = 0;
        
        m_curx = x;
        m_cury = y;
        m_font = g2d.getFont();
        m_frc = g2d.getFontRenderContext();
        m_color = g2d.getColor();
        s += '\n';
        ls = "";
        int index = 0;
        while (index < s.length()){
            char c = s.charAt(index++);
            switch(c){
                //����
                case '\n':
                    drawIt(g2d);
                    if (maxw < m_curx - x ) maxw = m_curx - x;
                    if (lh > 0){
                        m_cury += lh + 2; 
                        maxh = m_cury - y; 
                    }
                    m_curx = x; 
                    break;
                //ָ����ʽ    
                case '\t':
                    c = s.charAt(index++);
                    switch(c){
                        case 'b':
                           drawIt(g2d);
                           m_font = new Font(m_font.getName(), m_font.getStyle() | Font.BOLD, m_font.getSize());
                           break;
                        case 'B':
                           drawIt(g2d);
                           m_font = new Font(m_font.getName(), m_font.getStyle() & ~Font.BOLD, m_font.getSize());
                           break;
                        case 'i':
                           drawIt(g2d);
                           m_font = new Font(m_font.getName(), m_font.getStyle() | Font.ITALIC, m_font.getSize());
                           break;
                        case 'I':
                           drawIt(g2d);
                           m_font = new Font(m_font.getName(), m_font.getStyle() & ~Font.ITALIC, m_font.getSize());
                           break;
                        case 's':
                           drawIt(g2d);
                           int e = getNumEndPos(s, index);
                           if (e > index){
                                m_font = new Font(m_font.getName(), m_font.getStyle(), 
                                                Integer.parseInt(s.substring(index, e)));
                           }
                           index = e;
                           break;
                        case 'c':
                            drawIt(g2d);
                            try{
                                int rgb = 0;
                                //��ȡ8λ16��������
                                for (int i = 0; i < 8; i++){
                                    int cc = s.charAt(i+index);
                                    if (cc >= '0' && cc <= '9'){
                                        cc -= '0';
                                    }else if (cc >= 'A' && cc <= 'F'){
                                        cc = cc - 'A' + 10;
                                    }else if (cc >= 'a' && cc <= 'f'){
                                        cc = cc - 'a' + 10;
                                    }else break;
                                    rgb = rgb | (cc << ((7 - i)*4));    
                                    
                                }
                                m_color = new Color(rgb);
                                index += 8;    
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            break;
                        case 'e':
                            //��ͼ
                            drawIt(g2d);
                            int i = s.charAt(index++) - '0';
                            g2d.drawImage(emotion, m_curx - 3, m_cury - 4, 
                                                   m_curx + 24 - 3, m_cury + 24 - 4,
                                                   i * 24, 0, i * 24 + 24, 24,
                                                   null
                                          );
                            if (lh < 20) lh = 20;
                            m_curx += 20;
                            break;
                    }
                    break;    
                default:
                    ls = ls + c; 
            }
        }
        return new Dimension(maxw, maxh);
    }
}
