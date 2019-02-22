package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.PerTicket;
import Model.Performance;


public class PerformanceDAO {
	public static ArrayList<Performance> performanceDbArrayList = new ArrayList<>();
	public static ArrayList<PerTicket> perTicketDbArrayList = new ArrayList<>();
	public static ArrayList<PerTicket> perTicketSelectedTitleDbArrayList = new ArrayList<>();
	public static ArrayList<PerTicket> perTicketSelectedDateDbArrayList = new ArrayList<>();
	public static ArrayList<Performance> selectedPerformanceDbArrayList = new ArrayList<>();
	
	public static ArrayList<Integer> performanceGenreRatioDbArrayList = new ArrayList<>();
	public static ArrayList<Integer> performanceGenreRevenueDbArrayList = new ArrayList<>();
	
	public static int concert=0;
	public static int play=0;
	public static int musical=0;
	public static int exhibition=0;
	public static int festival=0;
	
	
	public static Performance t1selectedPerformance=null;
	// 1. 학생 등록 함수(등록버튼으로 stuObList에 넣은것을 DB에도 넣는다)
	public static int insertPerformanceData(Performance performance) {
		//1-1 데이터베이스 Performance테이블에 값을 입력하는 쿼리문
		StringBuffer insertPerformance= new StringBuffer();
		 insertPerformance.append("insert into performancetbl ");
		 insertPerformance.append("(performancecode, genre, performancetitle, location, performancedate, tag, runningtime, synopsis, casting, poster, produce, notice) ");
		 insertPerformance.append("values ");
		 insertPerformance.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		
		//1-2 데이터베이스 Connection을 가져옴
		Connection con = null;

		//1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt=null;
		
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement( insertPerformance.toString());
			//1-4 쿼리문에 실제 Performance 데이터를 연결
			
			psmt.setString(1, performance.getPerformanceCode());
			psmt.setString(2, performance.getGenre());
			psmt.setString(3, performance.getPerformanceTitle());
			psmt.setString(4, performance.getLocation());
			psmt.setString(5, performance.getPerformanceDate());
			psmt.setString(6, performance.getTag());
			psmt.setString(7, performance.getRunningTime());
			psmt.setString(8, performance.getSynopsis());
			psmt.setString(9, performance.getCasting());
			psmt.setString(10, performance.getPoster());
			psmt.setString(11, performance.getProduce());
			psmt.setString(12, performance.getNotice());
			
			
			//1-5 연결한 데이터를 쿼리문으로 실행(excuteUpdate() : 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문)
			count = psmt.executeUpdate();
			if(count==0) {
				MainController.callAlert("삽입 실패 : 데이터베이스 삽입 쿼리문 실패");
				return count;
			}

		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
			e.printStackTrace();
		}finally{
			//1-6. 자원객체를 닫아준다.
			try {
				if(psmt!=null) {psmt.close();}
				if(con!=null) {con.close();}
			}
			catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return count;
	}

	// 2. 테이블에서 퍼포먼스 전체내용을 모두 가져오는 함수
	public static ArrayList<Performance> getPerformanceTotalData() {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
		String selectPerformance = "select * from performancetbl";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectPerformance);

			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Performance performance = new Performance(rs.getString(1), rs.getString(3), rs.getString(2),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
				performanceDbArrayList.add(performance);
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return performanceDbArrayList;
	}
	// 2-1. 테이블에서 perTicket 전체내용을 모두 가져오는 함수
	public static ArrayList<PerTicket> getPerTicketTotalData() {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
		String selectPerTicket = "select p.performancecode, p.genre, p.performancetitle, p.location, p.performancedate, p.tag, p.runningtime, (select count(*) from tickettbl as t where grade='R' and p.performancecode=t.performancecode and customer is null) as 'R', (select count(*) from tickettbl as t where grade='A' and p.performancecode=t.performancecode and customer is null) as 'A', (select count(*) from tickettbl as t where grade='B' and p.performancecode=t.performancecode and customer is null) as 'B' from performancetbl as p";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectPerTicket);

			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				PerTicket perTicket = new PerTicket(
						rs.getString(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getInt(8), 
						rs.getInt(9),
						rs.getInt(10)
						);
				perTicketDbArrayList.add(perTicket);
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return perTicketDbArrayList;
	}
	// 2-2. 테이블에서 perTicket 제목 검색 내용을 모두 가져오는 함수
	public static ArrayList<PerTicket> getPerTicketSelectedTitleData(String selectedPerformanceTitle) {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
		String selectPerTicket = "select p.performancecode, p.genre, p.performancetitle, p.location, p.performancedate, p.tag, p.runningtime, (select count(*) from tickettbl as t where grade='R' and p.performancecode=t.performancecode and customer is null) as 'R', (select count(*) from tickettbl as t where grade='A' and p.performancecode=t.performancecode and customer is null) as 'A', (select count(*) from tickettbl as t where grade='B' and p.performancecode=t.performancecode and customer is null) as 'B' from performancetbl as p where performancetitle='"+selectedPerformanceTitle+"'";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectPerTicket);

			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				PerTicket perTicket = new PerTicket(
						rs.getString(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getInt(8), 
						rs.getInt(9),
						rs.getInt(10)
						);
				perTicketSelectedTitleDbArrayList.add(perTicket);
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
			e.printStackTrace();
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return perTicketSelectedTitleDbArrayList;
	}
	// 2-3. 테이블에서 perTicket 날짜 검색 내용을 모두 가져오는 함수
	public static ArrayList<PerTicket> getPerTicketSelectedDateData(String selectedPerformanceStartDate, String selectedPerformanceLastDate) {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
		String selectPerTicket = "select p.performancecode, p.genre, p.performancetitle, p.location, p.performancedate, p.tag, p.runningtime, (select count(*) from tickettbl as t where grade='R' and p.performancecode=t.performancecode and customer is null) as 'R', (select count(*) from tickettbl as t where grade='A' and p.performancecode=t.performancecode and customer is null) as 'A', (select count(*) from tickettbl as t where grade='B' and p.performancecode=t.performancecode and customer is null) as 'B' from performancetbl as p where performanceDate>='"+selectedPerformanceStartDate+"' and performancedate<='"+selectedPerformanceLastDate+"' ";   
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectPerTicket);

			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				PerTicket perTicket = new PerTicket(
						rs.getString(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getInt(8), 
						rs.getInt(9),
						rs.getInt(10)
						);
				perTicketSelectedDateDbArrayList.add(perTicket);
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
			e.printStackTrace();
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return perTicketSelectedDateDbArrayList;
	}

	// 2-4. t1테이블에서 선택된 퍼포먼스 전체내용을 가져오는 함수
	public static ArrayList<Performance> getPerformanceSelectedData(String performanceCode) {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
				String selectPerformance = "select * from performancetbl where performancecode='"+performanceCode+"'";
				// 2-2 데이터베이스 Connection을 가져옴
				Connection con = null;
				// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
				PreparedStatement psmt = null;
				// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
				ResultSet rs = null;
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(selectPerformance);

					// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
					rs = psmt.executeQuery();
					if (rs == null) {
						MainController.callAlert("select 실패 : select 쿼리문 실패");
						return null;
					}
					while (rs.next()) {
						Performance performance = new Performance(rs.getString(1), rs.getString(3), rs.getString(2),
								rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
								rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
						selectedPerformanceDbArrayList.add(performance);
					}
				} catch (SQLException e) {
					MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
				} finally {
					// 1-6. 자원객체를 닫아준다.
					try {
						if (psmt != null) {
							psmt.close();
						}
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
					}
				}
				return selectedPerformanceDbArrayList;
	}
		
	// 2-5 t3piechart genre ratio 를 가져오는 함수
	public static ArrayList<Integer> getPerformanceGenreRatioData() {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
				String selectPerformance = "select (select count(*) from performancetbl where genre='concert') as concert, " + 
						"(select count(*) from performancetbl where genre='play') as play, " + 
						"(select count(*) from performancetbl where genre='musical') as musical, " + 
						"(select count(*) from performancetbl where genre='exhibition') as exhibition, " + 
						"(select count(*) from performancetbl where genre='festival') as festival";
				// 2-2 데이터베이스 Connection을 가져옴
				Connection con = null;
				// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
				PreparedStatement psmt = null;
				// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
				ResultSet rs = null;
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(selectPerformance);

					// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
					rs = psmt.executeQuery();
					if (rs == null) {
						MainController.callAlert("select 실패 : select 쿼리문 실패");
						return null;
					}
					while (rs.next()) {
						concert=rs.getInt(1);
						play=rs.getInt(2);
						musical=rs.getInt(3);
						exhibition=rs.getInt(4);
						festival=rs.getInt(5);
					}
					performanceGenreRatioDbArrayList.add(concert);
					performanceGenreRatioDbArrayList.add(play);
					performanceGenreRatioDbArrayList.add(musical);
					performanceGenreRatioDbArrayList.add(exhibition);
					performanceGenreRatioDbArrayList.add(festival);
				} catch (SQLException e) {
					MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
				} finally {
					// 1-6. 자원객체를 닫아준다.
					try {
						if (psmt != null) {
							psmt.close();
						}
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
					}
				}
				return performanceGenreRatioDbArrayList;
	}

	// 2-6 t3BarChartGenre 선택된 장르의 매출 을 가져오는 함수
	public static ArrayList<Integer> getPerformanceGenreRevenue(String genre) {
		// 2-1 데이터베이스에 학생테이블 레코드를 모두 가져오는 쿼리문
		performanceGenreRevenueDbArrayList.clear();
		String selectPerformance = "select sum(total.price) from (select genre, nt.ticketname, price from performancetbl as pt1 inner join" + 
				"	(select performancecode, ticketname, price from tickettbl where customer is not null group by ticketname) as nt" + 
				"   where nt.performancecode=pt1.performancecode and genre='"+genre+"') as total;";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectPerformance);

			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				int genreRevenue = rs.getInt(1);
				performanceGenreRevenueDbArrayList.add(genreRevenue);
			}
		} catch (SQLException e) {
			MainController.callAlert("삽입 실패 : 데이터베이스에 삽입 실패");
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return performanceGenreRevenueDbArrayList;
	}

	// 3. 테이블뷰에서 선택한 레코드를 데이타베이스에서 삭제하는 함수
	public static int deletePerformanceData(String selectedPerformanceCode) {
		// 3-1 데이터베이스에 학생테이블 레코드를 삭제하는 쿼리문
		String deletePerformance = "delete from performancetbl where performancecode = ? ";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deletePerformance);
			psmt.setString(1, selectedPerformanceCode);
			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("delete 실패 : delete 쿼리문 실패");
				return count;
			}
		} catch (SQLException e) {
			MainController.callAlert("삭제 실패 : 데이터베이스에 삭제 실패");
		} finally {
			// 1-6. 자원객체를 닫아준다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return count;
	}

	// 4. 테이블뷰에서 수정한 레코드를 데이터베이스 테이블에 수정
	public static int updatePerformanceData(String selectedPeformanceCode, Performance editSelectedPerformance) {
		StringBuffer editPerformance = new StringBuffer();
		editPerformance.append("update performancetbl set ");
		editPerformance.append("performancecode=?, genre=?, performancetitle=?, location=?, performancedate=?, tag=?, runningtime=?, synopsis=?, casting=?, poster=?, produce=?, notice=?   where performancecode=? ");
		//4-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		//4-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt=null;
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(editPerformance.toString());
			//4-4 쿼리문에 실제 Ticket 데이터를 연결
		
			psmt.setString(1, editSelectedPerformance.getPerformanceCode());
			psmt.setString(2, editSelectedPerformance.getGenre());
			psmt.setString(3, editSelectedPerformance.getPerformanceTitle());
			psmt.setString(4, editSelectedPerformance.getLocation());
			psmt.setString(5, editSelectedPerformance.getPerformanceDate());
			psmt.setString(6, editSelectedPerformance.getTag());
			psmt.setString(7, editSelectedPerformance.getRunningTime());
			psmt.setString(8, editSelectedPerformance.getSynopsis());
			psmt.setString(9, editSelectedPerformance.getCasting());
			psmt.setString(10, editSelectedPerformance.getPoster());
			psmt.setString(11, editSelectedPerformance.getProduce());
			psmt.setString(12, editSelectedPerformance.getNotice());
			psmt.setString(13, selectedPeformanceCode);
			
			//4-5 연결한 데이터를 쿼리문으로 실행(excuteUpdate() : 쿼리문을 실행해서 테이블에 수정을 할때 사용하는 번개문)
			count = psmt.executeUpdate();
			if(count==0) {
				MainController.callAlert("수정실패 : 데이터베이스 수정 쿼리문 실패");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MainController.callAlert("수정 실패 : 데이터베이스에 수정 실패");
		}finally{
			//1-6. 자원객체를 닫아준다.
			try {
				if(psmt!=null) {psmt.close();}
				if(con!=null) {con.close();}
			}
			catch (SQLException e) {
				MainController.callAlert("자원 닫기 실패 : 최종 자원 닫기 실패");
			}
		}
		return count;
	}
	
}
