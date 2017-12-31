import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class PolylineBarChart {
	
	PolylineBarChart()
	{
		 PolylineBarChart demo = new PolylineBarChart();
	     JFreeChart chart = demo.getChart();
	       
	     JFrame f = new JFrame("Barchart");
	     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     f.setSize(650,500); 
	     f.setResizable(false);
	       
	     ChartPanel chartPanel = new ChartPanel(chart);
	     f.add(chartPanel);
	       
	     f.setVisible(true);
	}
 
   
    public JFreeChart getChart()
    {
    	int sNum[] = new int[13];
    	int kNum[] = new int[13];
    	int iNum[] = new int[13];
   
    	
    	AccidentCaseDAO dao = new AccidentCaseDAO();
    	ArrayList<AccidentCase> datas = new ArrayList<AccidentCase>();
    	
    	dao.connectTest("heeho", "1234");
    	datas = dao.searchCaseTime("2014");
    	
    	for(int iter = 0 ; iter < datas.size(); iter++)
    		{
    			if(datas.get(iter).getProvince().equals("서울특별시"))
    			{
    				sNum[Integer.parseInt(datas.get(iter).getMonth())]++;
    			}
    			else if(datas.get(iter).getProvince().equals("경기도"))
    			{
    				kNum[Integer.parseInt(datas.get(iter).getMonth())]++;
    			}
    			else if(datas.get(iter).getProvince().equals("인천광역시"))
    			{
    				iNum[Integer.parseInt(datas.get(iter).getMonth())]++;
    			}
    		}
    	
        // 데이터 생성
        DefaultCategoryDataset sDataSet = new DefaultCategoryDataset();                // bar chart 
        DefaultCategoryDataset kDataSet = new DefaultCategoryDataset();               // bar chart 2
        DefaultCategoryDataset iDataSet = new DefaultCategoryDataset();                // line chart 1

        // 데이터 입력 ( 값, 범례, 카테고리 )
        
        // 그래프 1      
        sDataSet.addValue(sNum[1], "서울", "1월");
        sDataSet.addValue(sNum[2], "서울", "2월");
        sDataSet.addValue(sNum[3], "서울", "3월");
        sDataSet.addValue(sNum[4], "서울", "4월");
        sDataSet.addValue(sNum[5], "서울", "5월");
        sDataSet.addValue(sNum[6], "서울", "6월");
        sDataSet.addValue(sNum[7], "서울", "7월");
        sDataSet.addValue(sNum[8], "서울", "8월");
        sDataSet.addValue(sNum[9], "서울", "9월");
        sDataSet.addValue(sNum[10], "서울", "10월");
        sDataSet.addValue(sNum[11], "서울", "11월");
        sDataSet.addValue(sNum[12], "서울", "12월");
        
        // 그래프 2       
        kDataSet.addValue(kNum[1], "경기", "1월");
        kDataSet.addValue(kNum[2], "경기", "2월");
        kDataSet.addValue(kNum[3], "경기", "3월");
        kDataSet.addValue(kNum[4], "경기", "4월");
        kDataSet.addValue(kNum[5],  "경기", "5월");
        kDataSet.addValue(kNum[6],  "경기", "6월");
        kDataSet.addValue(kNum[7],  "경기", "7월");
        kDataSet.addValue(kNum[8],  "경기", "8월");
        kDataSet.addValue(kNum[9], "경기", "9월");
        kDataSet.addValue(kNum[10],  "경기", "10월");
        kDataSet.addValue(kNum[11],  "경기", "11월");
        kDataSet.addValue(kNum[12],  "경기", "12월");

        // 그래프 3       
        iDataSet.addValue(iNum[1], "인천", "1월");
        iDataSet.addValue(iNum[2], "인천", "2월");
        iDataSet.addValue(iNum[3], "인천", "3월");
        iDataSet.addValue(iNum[4], "인천", "4월");
        iDataSet.addValue(iNum[5], "인천", "5월");
        iDataSet.addValue(iNum[6], "인천", "6월");
        iDataSet.addValue(iNum[7], "인천", "7월");
        iDataSet.addValue(iNum[8], "인천", "8월");
        iDataSet.addValue(iNum[9], "인천", "9월");
        iDataSet.addValue(iNum[10], "인천", "10월");
        iDataSet.addValue(iNum[11], "인천", "11월");
        iDataSet.addValue(iNum[12], "인천", "12월");

        // 렌더링 생성 및 세팅

        // 렌더링 생성
        
        final LineAndShapeRenderer sRender = new LineAndShapeRenderer();
        final LineAndShapeRenderer kRender = new LineAndShapeRenderer();
        final LineAndShapeRenderer iRender = new LineAndShapeRenderer();

        // 공통 옵션 정의
        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
        final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);

        Font f = new Font("Gothic", Font.BOLD, 15);
        Font axisF = new Font("Gulim", Font.PLAIN, 14);

        // 렌더링 세팅
        // 그래프 1
        sRender.setSeriesPaint(0, new Color(0,0,255));
        sRender.setBaseItemLabelFont(f);
        sRender.setBasePositiveItemLabelPosition(p_center);
        sRender.setBaseItemLabelGenerator(generator);
        sRender.setBaseItemLabelsVisible(true);

        // 그래프 2       
        kRender.setSeriesPaint(0, new Color(255,0,0));
        kRender.setBaseItemLabelFont(f);
        kRender.setBasePositiveItemLabelPosition(p_center);
        kRender.setBaseItemLabelGenerator(generator);
        kRender.setBaseItemLabelsVisible(true);

        // 그래프 3       
        iRender.setSeriesPaint(0, new Color(0,255,0));
        iRender.setBaseItemLabelFont(f);
        iRender.setBasePositiveItemLabelPosition(p_center);
        iRender.setBaseItemLabelGenerator(generator);
        iRender.setBaseItemLabelsVisible(true);

        // plot 생성
        final CategoryPlot plot = new CategoryPlot();

        // plot 에 데이터 적재
        plot.setDataset(sDataSet);
        plot.setRenderer(sRender);
        plot.setDataset(1,kDataSet);
        plot.setRenderer(1,kRender);
        plot.setDataset(2, iDataSet);
        plot.setRenderer(2, iRender);

        // plot 기본 설정

        plot.setOrientation(PlotOrientation.VERTICAL);             // 그래프 표시 방향
        plot.setRangeGridlinesVisible(true);                       // X축 가이드 라인 표시여부
        plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부


        // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       
        // X축 세팅

        plot.setDomainAxis(new CategoryAxis());              // X축 종류 설정
        plot.getDomainAxis().setTickLabelFont(axisF);  		 // X축 눈금라벨 폰트 조정
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정

        // Y축 세팅

        plot.setRangeAxis(new NumberAxis());                 // Y축 종류 설정
        plot.getRangeAxis().setTickLabelFont(axisF);        // Y축 눈금라벨 폰트 조정


        // 세팅된 plot을 바탕으로 chart 생성

        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("사망 교통 사고 월별 발생 건수"); // 차트 타이틀
        return chart;
    }
}

 
