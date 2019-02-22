package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketDAO {
	public static ArrayList<Ticket> dbRemainTicketArrayList = new ArrayList<>();
	public static ArrayList<Ticket> dbSellTicketArrayList = new ArrayList<>();
	public static ArrayList<Integer> TicketPriceList = new ArrayList<>();
	public static ArrayList<Integer> remainTicketCountList = new ArrayList<>();
	public static ArrayList<Integer> sellTicketCountList = new ArrayList<>();
	
	public static ArrayList<Integer> quarterlyRevenueList = new ArrayList<>();

	public static int priceR = 0;
	public static int priceA = 0;
	public static int priceB = 0;

	public static int remainRCount = 0;
	public static int sellRCount = 0;

	public static int remainACount = 0;
	public static int remainBCount = 0;

	public static int sellACount = 0;
	public static int sellBCount = 0;

	public static int price = 0;
	
	public static int firstQuarter=0;
	public static int secondQuarter=0;
	public static int thirdQuarter=0;
	public static int fourthQuarter=0;
	
	
	

	// 1. 학생 등록 함수(등록버튼으로 stuObList에 넣은것을 DB에도 넣는다)
	public static int insertTicketData(Ticket ticket) {
		// 1-1 데이터베이스 Ticket테이블에 값을 입력하는 쿼리문
		StringBuffer insertTicket = new StringBuffer();
		insertTicket.append("insert into tickettbl ");
		insertTicket.append(
				"(performancecode, performancetitle, performancedate, grade, ticketname, price, customer, customerphone, selldate) ");
		insertTicket.append("values ");
		insertTicket.append("(?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		// 1-2 데이터베이스 Connection을 가져옴
		Connection con = null;

		// 1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;

		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertTicket.toString());
			// 1-4 쿼리문에 실제 Ticket 데이터를 연결

			psmt.setString(1, ticket.getPerformanceCode());
			psmt.setString(2, ticket.getPerformanceTitle());
			psmt.setString(3, ticket.getPerformanceDate());
			psmt.setString(4, ticket.getGrade());
			psmt.setString(5, ticket.getTicketName());
			psmt.setInt(6, ticket.getPrice());
			psmt.setString(7, ticket.getCustomer());
			psmt.setString(8, ticket.getCustomerPhone());
			psmt.setString(9, ticket.getSellDate());

			// 1-5 연결한 데이터를 쿼리문으로 실행(excuteUpdate() : 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문)
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("삽입 실패 : 데이터베이스 삽입 쿼리문 실패");
				return count;
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
		return count;
	}

	// 2. 테이블에서 남은 티켓을 모두 가져오는 함수
	public static ArrayList<Ticket> getSelectRemainTicketTotalData(String selectRemainTicket) {
		// 2-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String selectTicket = "select * from tickettbl where performancecode='" + selectRemainTicket
				+ "' and customer is null group by ticketname";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectTicket);
			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			Ticket ticket = null;
			dbRemainTicketArrayList.clear();
			while (rs.next()) {
				ticket = new Ticket(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
				dbRemainTicketArrayList.add(ticket);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return dbRemainTicketArrayList;
	}

	// 2-1. 테이블에서 판매된 티켓을 모두 가져오는 함수
	public static ArrayList<Ticket> getSelectSellTicketTotalData(String selectSellTicket) {
		// 2-1-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String selectTicket = "select * from tickettbl where performancecode='" + selectSellTicket
				+ "' and customer is not null group by ticketname";
		// 2-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-1-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectTicket);
			// 2-1-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			Ticket ticket = null;
			dbSellTicketArrayList.clear();
			while (rs.next()) {
				ticket = new Ticket(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
				dbSellTicketArrayList.add(ticket);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return dbSellTicketArrayList;
	}

	// 2-2 테이블에서 선택된 performanceCode의 티켓 가격을 가져오는 함수
	public static ArrayList<Integer> getSelectedTicketPriceData(String selectedPerformanceCode) {
		// 2-1-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String selectTicket = "select (select price from tickettbl where grade='R' and performancecode='"
				+ selectedPerformanceCode
				+ "' group by grade) as rprice, (select price from tickettbl where grade='A' and performancecode='"
				+ selectedPerformanceCode
				+ "' group by grade) as aprice, (select price from tickettbl where grade='B' and performancecode='"
				+ selectedPerformanceCode + "' group by grade) as bprice;";
		// 2-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-1-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectTicket);
			// 2-1-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				priceR = rs.getInt(1);
				priceA = rs.getInt(2);
				priceB = rs.getInt(3);
			}
			TicketPriceList.add(priceR);
			TicketPriceList.add(priceA);
			TicketPriceList.add(priceB);
		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return TicketPriceList;
	}

	// 3. 티켓 버튼 테이블뷰에서 선택한 레코드를 데이타베이스에서 삭제하는 함수
	public static int deleteTicketData(String ticketname) {
		// 3-1 데이터베이스에 학생테이블 레코드를 삭제하는 쿼리문
		String deleteTicket = "delete from tickettbl where ticketname = ? ";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deleteTicket);
			psmt.setString(1, ticketname);
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

	// 3-1. t1 테이블뷰에서 선택한 레코드를 데이타베이스에서 삭제하는 함수
	public static int deleteTicketDataFromT1TableView(String selectedPerformanceCode) {
		// 3-1 데이터베이스에 학생테이블 레코드를 삭제하는 쿼리문
		String deleteTicket = "delete from tickettbl where performancecode = ? ";
		// 2-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deleteTicket);
			psmt.setString(1, selectedPerformanceCode);
			// 2-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			count = psmt.executeUpdate();
			if (count == 0) {
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

	// 4. 테이블뷰에서 남아있는 티켓을 판 티켓으로 데이터베이스 테이블에 수정
	public static int ticketRemainToSell(Ticket ticketRemainToSell) {
		// 4-1 데이터베이스 Ticket테이블을 수정하는 쿼리문
		StringBuffer sellTicket = new StringBuffer();
		sellTicket.append("update tickettbl set ");
		sellTicket.append("customer=?, customerphone=?, selldate=? where ticketname=? ");
		// 4-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 4-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(sellTicket.toString());
			// 4-4 쿼리문에 실제 Ticket 데이터를 연결

			psmt.setString(1, ticketRemainToSell.getCustomer());
			psmt.setString(2, ticketRemainToSell.getCustomerPhone());
			psmt.setString(3, ticketRemainToSell.getSellDate());
			psmt.setString(4, ticketRemainToSell.getTicketName());

			// 4-5 연결한 데이터를 쿼리문으로 실행(excuteUpdate() : 쿼리문을 실행해서 테이블에 수정을 할때 사용하는 번개문)
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("수정실패 : 데이터베이스 수정 쿼리문 실패");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MainController.callAlert("수정 실패 : 데이터베이스에 수정 실패");
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

	// 4-1. 테이블뷰에서 팔린 티켓을 남아있는 티켓으로 데이터베이스 테이블에 수정
	public static int ticketSellToRemain(Ticket ticketSellToRemain) {
		// 4-1-1 데이터베이스 Ticket테이블을 수정하는 쿼리문
		StringBuffer sellTicket = new StringBuffer();
		sellTicket.append("update tickettbl set ");
		sellTicket.append("customer=?, customerphone=?, selldate=? where ticketname=? ");
		// 4-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 4-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		int count = 0;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(sellTicket.toString());
			// 4-1-4 쿼리문에 실제 Ticket 데이터를 연결

			psmt.setString(1, ticketSellToRemain.getCustomer());
			psmt.setString(2, ticketSellToRemain.getCustomerPhone());
			psmt.setString(3, ticketSellToRemain.getSellDate());
			psmt.setString(4, ticketSellToRemain.getTicketName());

			// 4-1-5 연결한 데이터를 쿼리문으로 실행(excuteUpdate() : 쿼리문을 실행해서 테이블에 수정을 할때 사용하는 번개문)
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("수정실패 : 데이터베이스 수정 쿼리문 실패");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MainController.callAlert("수정 실패 : 데이터베이스에 수정 실패");
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

	// 5-1. 선택된 공연의 티켓의 잔여수량을 가져오는 함수
	public static ArrayList<Integer> remainTicketCount(String ticket) {
		// 2-1-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String calRRemainTicket = "select (select count(*) from tickettbl where grade='R' and performancecode='"
				+ ticket + "' and customer is null) as 'R', "
				+ "(select count(*) from tickettbl where grade='A' and performancecode='" + ticket
				+ "' and customer is null) as 'A', "
				+ "(select count(*) from tickettbl where grade='B' and performancecode='" + ticket
				+ "' and customer is null) as 'B'";
		// 2-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-1-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(calRRemainTicket);
			// 2-1-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				remainRCount = rs.getInt(1);
				remainACount = rs.getInt(2);
				remainBCount = rs.getInt(3);

			}
			remainTicketCountList.add(remainRCount);
			remainTicketCountList.add(remainACount);
			remainTicketCountList.add(remainBCount);

		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return remainTicketCountList;
	}

	// 6-1. 선택된 공연의 티켓의 팔린수량을 가져오는 함수
	public static ArrayList<Integer> sellTicketCount(String ticket) {
		// 2-1-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String calRSellTicket = "select (select count(*) from tickettbl where grade='R' and performancecode='" + ticket
				+ "' and customer is not null) as 'R', (select count(*) from tickettbl where grade='A' and performancecode='"
				+ ticket
				+ "' and customer is not null) as 'A', (select count(*) from tickettbl where grade='B' and performancecode='"
				+ ticket + "' and customer is not null) as 'B';";
		// 2-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-1-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(calRSellTicket);
			// 2-1-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				sellRCount = rs.getInt(1);
				sellACount = rs.getInt(2);
				sellBCount = rs.getInt(3);

			}
			sellTicketCountList.add(sellRCount);
			sellTicketCountList.add(sellACount);
			sellTicketCountList.add(sellBCount);

		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return sellTicketCountList;
	}

	// 6-2. 공연의 분기별 매출액을 가져오는 함수
	public static ArrayList<Integer> sellTicketQuarterlyRevenue(String year) {
		// 2-1-1 데이터베이스에 남은 티켓 레코드를 모두 가져오는 쿼리문
		String quarterlyRevenue = "select " + 
				"(select sum(a.ftotal) from (select count(*)*price as ftotal from tickettbl where customer is not null and selldate<'"+year+"-04-01' and selldate>='"+year+"-01-01' group by ticketname) as a) as totalfirst, " + 
				"(select sum(b.stotal) from (select count(*)*price as stotal from tickettbl where customer is not null and selldate<'"+year+"-07-01' and selldate>='"+year+"-04-01' group by ticketname) as b) as totalsecond," + 
				"(select sum(c.ttotal) from (select count(*)*price as ttotal from tickettbl where customer is not null and selldate<'"+year+"-10-01' and selldate>='"+year+"-07-01' group by ticketname) as c) as totalthird, " + 
				"(select sum(d.ftotal) from (select count(*)*price as ftotal from tickettbl where customer is not null and selldate<'"+year+"-12-31' and selldate>='"+year+"-10-01' group by ticketname) as d) as totalfourth";
		// 2-1-2 데이터베이스 Connection을 가져옴
		Connection con = null;
		// 2-1-3 쿼리문을 실행해야할 Statement를 만들어야 함.
		PreparedStatement psmt = null;
		// 2-1-4 쿼리문을 실행하고 나서 가져와야할 레코드를 저장할 객체
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(quarterlyRevenue);
			// 2-1-5 연결한 데이터를 쿼리문으로 실행(excuteQuery()을 실행해서 테이블에서 가져올때 사용하는 번개문)
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				firstQuarter= rs.getInt(1);
				secondQuarter= rs.getInt(2);
				thirdQuarter = rs.getInt(3);
				fourthQuarter = rs.getInt(4);

			}
			quarterlyRevenueList.add(firstQuarter);
			quarterlyRevenueList.add(secondQuarter);
			quarterlyRevenueList.add(thirdQuarter);
			quarterlyRevenueList.add(fourthQuarter);

		}

		catch (SQLException e) {
			e.printStackTrace();
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
		return quarterlyRevenueList;
	}

}
