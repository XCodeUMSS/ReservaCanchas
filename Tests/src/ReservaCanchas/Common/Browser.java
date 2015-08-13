package ReservaCanchas.Common;

import java.awt.AWTException;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import net.sf.sahi.client.BrowserCondition;
import net.sf.sahi.client.ElementStub;
import net.sf.sahi.client.ExecutionException;

import org.apache.log4j.Logger;

import ReservaCanchas.common.Functions;
import com.jacob.com.LibraryLoader;

/**
 * @date 8/28/2013
 * @author Walter Ramirez
 */
public class Browser extends net.sf.sahi.client.Browser {

	private boolean objectVerification;
	/*
	 * Initializing a logger instance using the root logger
	 */
	private final Logger logger = Logger.getRootLogger();

	public Browser(String browserName) {
		super(browserName);
		this.objectVerification = false;
	}

	public Browser(String browserPath, String browserProcessName, String browserOption) {
		super(browserPath, browserProcessName, browserOption);
		this.objectVerification = false;
	}

	public Browser(String browserPath, String browserProcessName, String browserOption, String host, int port) {
		super(browserPath, browserProcessName, browserOption, host, port);
		this.objectVerification = false;
	}

	public Browser(String browserName, String host, int port) {
		super(browserName, host, port);
		this.objectVerification = false;
	}

	public Browser() {
		super();
		this.objectVerification = false;
	}

	/**
	 * Set the ObjectVerification Variable in order to enable or disable the exists and highlight options 
	 * @param objectVerif if TRUE the Highlight and exists options will be executed each time that an object is created
	 */
	public void setObjectVerification(boolean objectVerif){
		this.objectVerification = objectVerif;
	}

	@Override
	public ElementStub accessor(Object... args) {
		ElementStub acs = super.accessor(args);
		if (objectVerification)
			if (acs.exists(true) && acs.isVisible(true)) {
				acs.highlight();
			}
		return acs;
	}

	@Override
	public ElementStub button(Object... args) {
		ElementStub btn = super.button(args);
		if (objectVerification)
			if (btn.exists(true) && btn.isVisible(true)) {
				btn.highlight();
			}
		return btn;
	}

	@Override
	public ElementStub checkbox(Object... args) {
		ElementStub chk = super.checkbox(args);
		if (objectVerification)
			if (chk.exists(true) && chk.isVisible(true)) {
				chk.highlight();
			}
		return chk;
	}

	@Override
	public ElementStub image(Object... args) {
		ElementStub img = super.image(args);
		if (objectVerification)
			if (img.exists(true) && img.isVisible(true)) {
				img.highlight();
			}
		return img;
	}

	@Override
	public ElementStub imageSubmitButton(Object... args) {
		ElementStub isb = super.imageSubmitButton(args);
		if (objectVerification)
			if (isb.exists(true) && isb.isVisible(true)) {
				isb.highlight();
			}
		return isb;
	}

	@Override
	public ElementStub link(Object... args) {
		ElementStub lnk = super.link(args);
		if (objectVerification)
			if (lnk.exists(true) && lnk.isVisible(true)) {
				lnk.highlight();
			}
		return lnk;
	}

	@Override
	public ElementStub password(Object... args) {
		ElementStub psw = super.password(args);
		if (objectVerification)
			if (psw.exists(true) && psw.isVisible(true)) {
				psw.highlight();
			}
		return psw;
	}

	@Override
	public ElementStub radio(Object... args) {
		ElementStub rdb = super.radio(args);
		if (objectVerification)
			if (rdb.exists(true) && rdb.isVisible(true)) {
				rdb.highlight();
			}
		return rdb;
	}

	@Override
	public ElementStub select(Object... args) {
		ElementStub sel = super.select(args);
		if (objectVerification)
			if (sel.exists(true) && sel.isVisible(true)) {
				sel.highlight();
			}
		return sel;
	}

	@Override
	public ElementStub submit(Object... args) {
		ElementStub smt = super.submit(args);
		if (objectVerification)
			if (smt.exists(true) && smt.isVisible(true)) {
				smt.highlight();
			}
		return smt;
	}

	@Override
	public ElementStub textarea(Object... args) {
		ElementStub txa = super.textarea(args);
		if (objectVerification)
			if (txa.exists(true) && txa.isVisible(true)) {
				txa.highlight();
			}
		return txa;
	}

	@Override
	public ElementStub textbox(Object... args) {
		ElementStub txt = super.textbox(args);
		if (objectVerification)
			if (txt.exists(true) && txt.isVisible(true)) {
				txt.highlight();
			}
		return txt;
	}

	@Override
	public ElementStub cell(Object... args) {
		ElementStub cell = super.cell(args);
		if (objectVerification)
			if (cell.exists(true) && cell.isVisible(true)) {
				cell.highlight();
			}
		return cell;
	}

	@Override
	public ElementStub table(Object... args) {
		ElementStub tbl = super.table(args);
		if (objectVerification)
			if (tbl.exists(true) && tbl.isVisible(true)) {
				tbl.highlight();
			}
		return tbl;
	}

	@Override
	public ElementStub byId(Object... args) {
		ElementStub byId = super.byId(args);
		if (objectVerification)
			if (byId.exists(true) && byId.isVisible(true)) {
				byId.highlight();
			}
		return byId;
	}

	@Override
	public ElementStub byClassName(Object... args) {
		ElementStub bycn = super.byClassName(args);
		if (objectVerification)
			if (bycn.exists(true) && bycn.isVisible(true)) {
				bycn.highlight();
			}
		return bycn;
	}

	@Override
	public ElementStub byXPath(Object... args) {
		ElementStub byxp = super.byXPath(args);
		if (objectVerification)
			if (byxp.exists(true) && byxp.isVisible(true)) {
				byxp.highlight();
			}
		return byxp;
	}

	@Override
	public ElementStub bySeleniumLocator(Object... args) {
		ElementStub bysl = super.bySeleniumLocator(args);
		if (objectVerification)
			if (bysl.exists(true) && bysl.isVisible(true)) {
				bysl.highlight();
			}
		return bysl;
	}

	@Override
	public ElementStub row(Object... args) {
		ElementStub row = super.row(args);
		if (objectVerification)
			if (row.exists(true) && row.isVisible(true)) {
				row.highlight();
			}
		return row;
	}

	@Override
	public ElementStub div(Object... args) {
		ElementStub div = super.div(args);
		if (objectVerification)
			if (div.exists(true) && div.isVisible(true)) {
				div.highlight();
			}
		return div;
	}

	@Override
	public ElementStub span(Object... args) {
		ElementStub span = super.span(args);
		if (objectVerification)
			if (span.exists(true) && span.isVisible(true)) {
				span.highlight();
			}
		return span;
	}

	@Override
	public ElementStub activeElement(Object... args) {
		ElementStub ace = super.activeElement(args);
		if (objectVerification)
			if (ace.exists(true) && ace.isVisible(true)) {
				ace.highlight();
			}
		return ace;
	}

	@Override
	public ElementStub dList(Object... args) {
		ElementStub dlst = super.dList(args);
		if (objectVerification)
			if (dlst.exists(true) && dlst.isVisible(true)) {
				dlst.highlight();
			}
		return dlst;
	}

	@Override
	public ElementStub dTerm(Object... args) {
		ElementStub dterm = super.dTerm(args);
		if (objectVerification)
			if (dterm.exists(true) && dterm.isVisible(true)) {
				dterm.highlight();
			}
		return dterm;
	}

	@Override
	public ElementStub dDesc(Object... args) {
		ElementStub ddesc = super.dDesc(args);
		if (objectVerification)
			if (ddesc.exists(true) && ddesc.isVisible(true)) {
				ddesc.highlight();
			}
		return ddesc;
	}

	@Override
	public ElementStub abbr(Object... args) {
		ElementStub abbr = super.abbr(args);
		if (objectVerification)
			if (abbr.exists(true) && abbr.isVisible(true)) {
				abbr.highlight();
			}
		return abbr;
	}

	@Override
	public ElementStub paragraph(Object... args) {
		ElementStub par = super.paragraph(args);
		if (objectVerification)
			if (par.exists(true) && par.isVisible(true)) {
				par.highlight();
			}
		return par;
	}

	@Override
	public ElementStub option(Object... args) {
		ElementStub opt = super.option(args);
		if (objectVerification)
			if (opt.exists(true) && opt.isVisible(true)) {
				opt.highlight();
			}
		return opt;
	}

	@Override
	public ElementStub reset(Object... args) {
		ElementStub rst = super.reset(args);
		if (objectVerification)
			if (rst.exists(true) && rst.isVisible(true)) {
				rst.highlight();
			}
		return rst;
	}

	@Override
	public ElementStub file(Object... args) {
		ElementStub file = super.file(args);
		if (objectVerification)
			if (file.exists(true) && file.isVisible(true)) {
				file.highlight();
			}
		return file;
	}

	@Override
	public ElementStub byText(Object... args) {
		ElementStub byTxt = super.byText(args);
		if (objectVerification)
			if (byTxt.exists(true) && byTxt.isVisible(true)) {
				byTxt.highlight();
			}
		return byTxt;
	}

	@Override
	public ElementStub cookie(Object... args) {
		ElementStub cookie = super.cookie(args);
		if (objectVerification)
			if (cookie.exists(true) && cookie.isVisible(true)) {
				cookie.highlight();
			}
		return cookie;
	}

	@Override
	public ElementStub position(Object... args) {
		ElementStub pos = super.position(args);
		if (objectVerification)
			if (pos.exists(true) && pos.isVisible(true)) {
				pos.highlight();
			}
		return pos;
	}

	@Override
	public ElementStub label(Object... args) {
		ElementStub lbl = super.label(args);
		if (objectVerification)
			if (lbl.exists(true) && lbl.isVisible(true)) {
				lbl.highlight();
			}
		return lbl;
	}

	@Override
	public ElementStub list(Object... args) {
		ElementStub lst = super.list(args);
		if (objectVerification)
			if (lst.exists(true) && lst.isVisible(true)) {
				lst.highlight();
			}
		return lst;
	}

	@Override
	public ElementStub listItem(Object... args) {
		ElementStub lstIntem = super.listItem(args);
		if (objectVerification)
			if (lstIntem.exists(true) && lstIntem.isVisible(true)) {
				lstIntem.highlight();
			}
		return lstIntem;
	}

	@Override
	public ElementStub parentNode(Object... args) {
		ElementStub prntNd = super.parentNode(args);
		if (objectVerification)
			if (prntNd.exists(true) && prntNd.isVisible(true)) {
				prntNd.highlight();
			}
		return prntNd;
	}

	@Override
	public ElementStub parentCell(Object... args) {
		ElementStub prntCell = super.parentCell(args);
		if (objectVerification)
			if (prntCell.exists(true) && prntCell.isVisible(true)) {
				prntCell.highlight();
			}
		return prntCell;
	}

	@Override
	public ElementStub parentRow(Object... args) {
		ElementStub prntRow = super.parentRow(args);
		if (objectVerification)
			if (prntRow.exists(true) && prntRow.isVisible(true)) {
				prntRow.highlight();
			}
		return prntRow;
	}

	@Override
	public ElementStub parentTable(Object... args) {
		ElementStub prntTbl = super.parentTable(args);
		if (objectVerification)
			if (prntTbl.exists(true) && prntTbl.isVisible(true)) {
				prntTbl.highlight();
			}
		return prntTbl;
	}

	@Override
	public ElementStub rte(Object... args) {
		ElementStub rte = super.rte(args);
		if (objectVerification)
			if (rte.exists(true) && rte.isVisible(true)) {
				rte.highlight();
			}
		return rte;
	}

	@Override
	public ElementStub iframe(Object... args) {
		ElementStub ifrm = super.iframe(args);
		if (objectVerification)
			if (ifrm.exists(true) && ifrm.isVisible(true)) {
				ifrm.highlight();
			}
		return ifrm;
	}

	@Override
	public ElementStub tableHeader(Object... args) {
		ElementStub tblh = super.tableHeader(args);
		if (objectVerification)
			if (tblh.exists(true) && tblh.isVisible(true)) {
				tblh.highlight();
			}
		return tblh;
	}

	@Override
	public ElementStub heading1(Object... args) {
		ElementStub head1 = super.heading1(args);
		if (objectVerification)
			if (head1.exists(true) && head1.isVisible(true)) {
				head1.highlight();
			}
		return head1;
	}

	@Override
	public ElementStub heading2(Object... args) {
		ElementStub head2 = super.heading2(args);
		if (objectVerification)
			if (head2.exists(true) && head2.isVisible(true)) {
				head2.highlight();
			}
		return head2;
	}

	@Override
	public ElementStub heading3(Object... args) {
		ElementStub head3 = super.heading3(args);
		if (objectVerification)
			if (head3.exists(true) && head3.isVisible(true)) {
				head3.highlight();
			}
		return head3;
	}

	@Override
	public ElementStub heading4(Object... args) {
		ElementStub head4 = super.heading4(args);
		if (objectVerification)
			if (head4.exists(true) && head4.isVisible(true)) {
				head4.highlight();
			}
		return head4;
	}

	@Override
	public ElementStub heading5(Object... args) {
		ElementStub head5 = super.heading5(args);
		if (objectVerification)
			if (head5.exists(true) && head5.isVisible(true)) {
				head5.highlight();
			}
		return head5;
	}

	@Override
	public ElementStub heading6(Object... args) {
		ElementStub head6 = super.heading6(args);
		if (objectVerification)
			if (head6.exists(true) && head6.isVisible(true)) {
				head6.highlight();
			}
		return head6;
	}

	@Override
	public ElementStub hidden(Object... args) {
		ElementStub hidd = super.hidden(args);
		if (objectVerification)
			if (hidd.exists(true) && hidd.isVisible(true)) {
				hidd.highlight();
			}
		return hidd;
	}

	@Override
	public ElementStub area(Object... args) {
		ElementStub area = super.area(args);
		if (objectVerification)
			if (area.exists(true) && area.isVisible(true)) {
				area.highlight();
			}
		return area;
	}

	@Override
	public ElementStub map(Object... args) {
		ElementStub map = super.map(args);
		if (objectVerification)
			if (map.exists(true) && map.isVisible(true)) {
				map.highlight();
			}
		return map;
	}

	@Override
	public ElementStub italic(Object... args) {
		ElementStub ita = super.italic(args);
		if (objectVerification)
			if (ita.exists(true) && ita.isVisible(true)) {
				ita.highlight();
			}
		return ita;
	}

	@Override
	public ElementStub bold(Object... args) {
		ElementStub bold = super.bold(args);
		if (objectVerification)
			if (bold.exists(true) && bold.isVisible(true)) {
				bold.highlight();
			}
		return bold;
	}

	@Override
	public ElementStub emphasis(Object... args) {
		ElementStub emph = super.emphasis(args);
		if (objectVerification)
			if (emph.exists(true) && emph.isVisible(true)) {
				emph.highlight();
			}
		return emph;
	}

	@Override
	public ElementStub strong(Object... args) {
		ElementStub str = super.strong(args);
		if (objectVerification)
			if (str.exists(true) && str.isVisible(true)) {
				str.highlight();
			}
		return str;
	}

	@Override
	public ElementStub preformatted(Object... args) {
		ElementStub pref = super.preformatted(args);
		if (objectVerification)
			if (pref.exists(true) && pref.isVisible(true)) {
				pref.highlight();
			}
		return pref;
	}

	@Override
	public ElementStub code(Object... args) {
		ElementStub code = super.code(args);
		if (objectVerification)
			if (code.exists(true) && code.isVisible(true)) {
				code.highlight();
			}
		return code;
	}

	@Override
	public ElementStub blockquote(Object... args) {
		ElementStub blk = super.blockquote(args);
		if (objectVerification)
			if (blk.exists(true) && blk.isVisible(true)) {
				blk.highlight();
			}
		return blk;
	}

	@Override
	public ElementStub xy(Object... args) {
		ElementStub xy = super.xy(args);
		if (objectVerification)
			if (xy.exists(true) && xy.isVisible(true)) {
				xy.highlight();
			}
		return xy;
	}

	@Override
	public ElementStub datebox(Object... args) {
		ElementStub dtbox = super.datebox(args);
		if (objectVerification)
			if (dtbox.exists(true) && dtbox.isVisible(true)) {
				dtbox.highlight();
			}
		return dtbox;
	}

	@Override
	public ElementStub datetimebox(Object... args) {
		ElementStub dtbox = super.datetimebox(args);
		if (objectVerification)
			if (dtbox.exists(true) && dtbox.isVisible(true)) {
				dtbox.highlight();
			}
		return dtbox;
	}

	@Override
	public ElementStub datetimelocalbox(Object... args) {
		ElementStub dtbox = super.datetimelocalbox(args);
		if (objectVerification)
			if (dtbox.exists(true) && dtbox.isVisible(true)) {
				dtbox.highlight();
			}
		return dtbox;
	}

	@Override
	public ElementStub emailbox(Object... args) {
		ElementStub ebox = super.emailbox(args);
		if (objectVerification)
			if (ebox.exists(true) && ebox.isVisible(true)) {
				ebox.highlight();
			}
		return ebox;
	}

	@Override
	public ElementStub monthbox(Object... args) {
		ElementStub mbox = super.monthbox(args);
		if (objectVerification)
			if (mbox.exists(true) && mbox.isVisible(true)) {
				mbox.highlight();
			}
		return mbox;
	}

	@Override
	public ElementStub numberbox(Object... args) {
		ElementStub nbox = super.numberbox(args);
		if (objectVerification)
			if (nbox.exists(true) && nbox.isVisible(true)) {
				nbox.highlight();
			}
		return nbox;
	}

	@Override
	public ElementStub rangebox(Object... args) {
		ElementStub rbox = super.rangebox(args);
		if (objectVerification)
			if (rbox.exists(true) && rbox.isVisible(true)) {
				rbox.highlight();
			}
		return rbox;
	}

	@Override
	public ElementStub searchbox(Object... args) {
		ElementStub sbox = super.searchbox(args);
		if (objectVerification)
			if (sbox.exists(true) && sbox.isVisible(true)) {
				sbox.highlight();
			}
		return sbox;
	}

	@Override
	public ElementStub telbox(Object... args) {
		ElementStub tel = super.telbox(args);
		if (objectVerification)
			if (tel.exists(true) && tel.isVisible(true)) {
				tel.highlight();
			}
		return tel;
	}

	@Override
	public ElementStub timebox(Object... args) {
		ElementStub tbox = super.timebox(args);
		if (objectVerification)
			if (tbox.exists(true) && tbox.isVisible(true)) {
				tbox.highlight();
			}
		return tbox;
	}

	@Override
	public ElementStub urlbox(Object... args) {
		ElementStub urlbox = super.urlbox(args);
		if (objectVerification)
			if (urlbox.exists(true) && urlbox.isVisible(true)) {
				urlbox.highlight();
			}
		return urlbox;
	}
	 @Override
	 public ElementStub weekbox(Object... args) {
		 ElementStub wbox = super.weekbox(args);
		 if (objectVerification)
			 if (wbox.exists(true) && wbox.isVisible(true)) {
				 wbox.highlight();
			 }
		 return wbox;
	 }

	 //--------------------- Finished Overriding objects ----------------------
	 //--------------------------- Overriding Actions ---------------------------
	 @Override
	 public void click(ElementStub element) throws ExecutionException {
		 logger.debug("Click on: " + element.getText());
		 super.click(element);
		 zkWaitLoad();
	 }

	 /**
	  * Wait until the "loading" image disappears from the screen using a
	  * default timeout of 5 minutes before to raise an exception
	  */
	  public void zkWaitLoad() {
		 zkWaitLoad(5);
	 }

	 /**
	  * Wait until the "loading" image disappears from the screen using a
	  * specific timeout.
	  * @param timeoutMinutes Minutes before to raise an exception
	  */
	  public void zkWaitLoad(int timeoutMinutes) {
		 int timeoutSeconds = timeoutMinutes * 60;
		 int intervalSeconds = 5;
		 while(super.exists(super.span("z-loading-icon"), true) && timeoutSeconds > 0){
			 super.waitFor(intervalSeconds * 1000);
			 timeoutSeconds -= intervalSeconds;
		 }
		 if (super.exists(super.span("z-loading-icon"), true))
			 new Exception("Error: the Server take more than:" + timeoutMinutes + " minutes.");
	 }

	 @Override
	 public void setValue(ElementStub textbox, String value) throws ExecutionException {
		 logger.debug("Set: " + value + ", in: " + textbox);
		 super.focus(textbox);
		 super.setValue(textbox, value);
	 }

	 @Override
	 public void setFile(ElementStub textbox, String value) throws ExecutionException {
		 logger.debug("Set file: " + value + ", in: " + textbox);
		 super.focus(textbox);
		 super.setFile(textbox, value);
	 }

	 @Override
	 public void setFile(ElementStub textbox, String value, String URL) throws ExecutionException {
		 logger.debug("Set file: " + value + " and the URL " + URL + ", in: " + textbox);
		 super.focus(textbox);
		 super.setFile(textbox, value, URL);
	 }

	 @Override
	 public void keyUp(ElementStub element, int keyCode, int charCode) {
		 super.keyUp(element, keyCode, charCode);
	 }

	 @Override
	 public void keyDown(ElementStub elementStub, String keySequence, String combo) throws ExecutionException {
		 logger.debug("Simulating the key down secuence: " + keySequence + " in: " + elementStub + " with this combo: " + combo);
		 super.keyDown(elementStub, keySequence, combo);
	 }

	 @Override
	 public void keyDown(ElementStub elementStub, String keySequence) throws ExecutionException {
		 logger.debug("Simulating the key down secuence: " + keySequence + " in: " + elementStub);
		 super.keyDown(elementStub, keySequence);
	 }

	 @Override
	 public void keyDown(ElementStub element, int keyCode, int charCode) {
		 super.keyDown(element, keyCode, charCode);
	 }

	 @Override
	 public void keyPress(ElementStub elementStub, String keySequence, String combo) throws ExecutionException {
		 logger.debug("Simulating the KeyPress action in: " + elementStub);
		 super.keyPress(elementStub, keySequence, combo);
	 }

	 @Override
	 public void keyPress(ElementStub elementStub, String keySequence) throws ExecutionException {
		 logger.debug("Simulating the KeyPress action in: " + elementStub);
		 super.keyPress(elementStub, keySequence);
	 }

	 @Override
	 public void blur(ElementStub elementStub) {
		 logger.debug("Blur the: " + elementStub);
		 super.blur(elementStub);
	 }

	 @Override
	 public void doubleClick(ElementStub element) throws ExecutionException {
		 logger.debug("Double click over: " + element);
		 super.doubleClick(element);
	 }

	 @Override
	 public void rightClick(ElementStub element) throws ExecutionException {
		 logger.debug("Double click over: " + element);
		 super.rightClick(element);
	 }

	 @Override
	 public void check(ElementStub element) throws ExecutionException {
		 if (checked(element)){
			 logger.debug("Warning: The " + element + " is already checked.");
		 }
		 logger.debug("Check the: " + element);
		 focus(element);
		 super.check(element);
	 }

	 @Override
	 public void uncheck(ElementStub element) throws ExecutionException {
		 if (!checked(element)){
			 logger.debug("Warning: The " + element + " is already unchecked.");
		 }
		 logger.debug("Uncheck the: " + element);
		 focus(element);
		 super.uncheck(element);
	 }

	 @Override
	 public void focus(ElementStub element) throws ExecutionException {
		 logger.debug("Focusing: " + element);
		 super.focus(element);
	 }

	 @Override
	 public void removeFocus(ElementStub element) throws ExecutionException {
		 logger.debug("Removing the focus from: " + element);
		 super.removeFocus(element);
	 }

	 @Override
	 public void mouseOver(ElementStub element) throws ExecutionException {
		 logger.debug("Moving the mouse over: " + element);
		 super.mouseOver(element);
	 }

	 @Override
	 public void mouseDown(ElementStub element) throws ExecutionException {
		 logger.debug("Moving the mouse down: " + element);
		 super.mouseDown(element);
	 }

	 @Override
	 public void mouseUp(ElementStub element) throws ExecutionException {
		 logger.debug("Moving the mouse up: " + element);
		 super.mouseUp(element);
	 }

	 @Override
	 public void dragDrop(ElementStub dragElement, ElementStub dropElement) throws ExecutionException {
		 logger.debug("Drag the: " + dragElement + ", and drop in:" + dropElement);
		 super.dragDrop(dragElement, dropElement);
	 }

	 @Override
	 public void dragDropXY(ElementStub dragElement, int x, int y) throws ExecutionException {
		 logger.debug("Drag the: " + dragElement + ", and drop in the coordinates:" + x + ", " + y);
		 super.dragDropXY(dragElement, x, y);
	 }

	 @Override
	 public void choose(ElementStub elementStub, String[] values, boolean append) throws ExecutionException {
		 String vals = "{";
		 for(String val :values){
			 vals = vals + val;
		 }
		 vals = vals + "}";
		 logger.debug("Choosing the values: " + vals + " from: " + elementStub);
		 focus(elementStub);
		 super.choose(elementStub, values, append);
	 }
	 /**
	  * Check multiple elements
	  * @param elementType the element type as "span", "div", "label", "image"
	  * that contains the necessary objects to check
	  * @param objects Name IDs of the objects that we need to check
	  * @param nearObject A common object that is near of the objects to perform the check action
	  */
	 public void multyCheckboxChoise(String elementType, String[] objects, ElementStub nearObject){
		 //Unchecking all the Checkboxes of the group
		 boolean exists = super.checkbox("0").near(nearObject).exists(true);
		 if (exists){
			 int item = 0;
			 while (exists){
				 super.checkbox(String.valueOf(item)).near(nearObject).uncheck();
				 item ++;
				 exists = super.checkbox(String.valueOf(item)).near(nearObject).exists(true);
			 }
		 }
		 ElementStub[] elements = Functions.ObjectsGenerator(elementType, objects, this);
		 //Check all the necessary objects using the object type sent to the function of there as a reference
		 for(ElementStub element : elements){
			 element.near(nearObject).click();
		 }
	 }

	 /**
	  * Checks the image checkbox displayed in the left side of an specific object
	  * 
	  * @param element Near element of the Image Checkbox that we need to check 
	  * @throws ExecutionException
	  */
	 public void checkImage(ElementStub element) throws ExecutionException {
		 if (imageChecked(browser.image(1).near(element))){
			 logger.debug("Warning: The " + element + " is already checked.");
		 }
		 else{
			 logger.debug("Check the: " + element);
			 focus(browser.image(1).near(element));
			 browser.image(1).near(element).click();
		 }
	 }

	 /**
	  * Checks the image checkbox displayed in the left side of an specific object
	  * 
	  * @param element Near element of the Image Checkbox that we need to uncheck
	  * @throws ExecutionException
	  */
	 public void uncheckImage(ElementStub element) throws ExecutionException {
		 if (!imageChecked(imageCheckbox(element))){
			 logger.debug("Warning: The " + element + " is already unchecked.");
		 }
		 else{
			 logger.debug("Uncheck the: " + element);
			 focus(imageCheckbox(element));
			 imageCheckbox(element).click();
		 }
	 }

	 /**
	  * Verify if an image checkbox is displayed as checked or unchecked
	  * @param element the image checkbox that we need to verify
	  * @return
	  */
	 public boolean imageChecked(ElementStub element){
		 CharSequence cs = "checkbox-checked".subSequence(0, 16);
		 return element.getAttribute("src").contains(cs);
	 }
	 //------------------------- End Overriding Actions -------------------------
	 @Override
	 public void navigateTo(String url) throws ExecutionException {
		 logger.debug("Navigating to: " + url);
		 super.navigateTo(url);
	 }

	 @Override
	 public void navigateTo(String url, boolean forceReload) throws ExecutionException {
		 logger.debug("Navigating to: " + url + ", with a force reload action");
		 super.navigateTo(url, forceReload);
	 }
	 /**
	  * Waits for any browser object by 5 seconds searching by the "exists" property
	  * @param object Browser object to wait
          * @return true if the object exists at the end of the execution
          */
	 public boolean waitObject(ElementStub object){
		 return waitObject(object, 5000);
	 }
	 /**
	  * Waits for any browser object by 5 seconds searching by the "exists" property
	  * @param object Browser object to wait
	  * @param timeout timeout in millisecond before to continue the execution
          * @return true if the object exists at the end of the execution
	  */
	 public boolean waitObject(final ElementStub object, int timeout){
		 logger.debug("Waiting for: \"" + object + "\", with a tiemout of: " + timeout + " milliseconds");
		 BrowserCondition condition = new BrowserCondition(this){
			 @Override
			 public boolean test() throws ExecutionException {
				 return object.exists(true);
			 }
		 };
		 this.waitFor(condition, timeout);
		 return object.exists(true);
	 }
	 /**
	  * Returns an image checkbox of the near object to a label
	  * @param nearElement Near Div or Span element of the image checkbox
	  * @return
	  */
	 public ElementStub imageCheckbox(ElementStub nearElement){
		 return image(1).near(nearElement);
	 }

	 /**
	  * Function that allows the use of the zk combo box objects and returns
	  * the Element Stub referred to the elementName
	  * 
	  * @param args  <p>
	  * Name of the element to retrieve <p>
	  * (Optional) Near element <p>
	  * (Optional) Integer Id of the Italic expand element
	  * @return the selected element
	  */
	 public ElementStub zkComboBox(Object... args) {
		 switch (args.length){
		 case 1: italic(1).click(); break;
		 case 2: italic(1).near((ElementStub) args[1]).click(); break;
		 case 3: italic((int)args[2]).near((ElementStub) args[1]).click(); break;
		 default: logger.info("Any extra argument will not be considered");
		 }
		 zkWaitLoad();
		 return getElement((String)args[0]);
	 }
	 /*
	  * return an element using as reference the table container
	  */
	 private ElementStub getElement(String elementName){
		 //detecting the displayed table and returning the selected element
		 ElementStub tbl = table("z-combobox-cave");
		 if (tbl.exists(true) && tbl.isVisible(true)) {
			 return cell(elementName).in(tbl);
		 }else{
			 for(int i = 1; i<21; i++){
				 tbl = table("z-combobox-cave[" + i + "]");
				 if (tbl.exists(true) && tbl.isVisible(true)) {
					 return cell(elementName).in(tbl);
				 }
			 }
		 }
		 return tbl;
	 }

	 /**
	  * Add a file in order to upload it to the browser
	  * @param file File Element when we need to perform the click in order to show the Open dialog
	  * @param filePath path of the file that we need to upload
	  * @throws AWTException 
	  */
	 public void setFileUpload(ElementStub file, String filePath) throws AWTException {
		 //Loading Jacob and AutoITX3 DLL Libraries
		 loadAutoIT();
		 //Loading AutoITX Library
		 AutoItX x = new AutoItX();

		 filePath = System.getProperty("user.dir") + File.separator + filePath;
		 if (isFF()){
			 super.click(file);
			 String upload = "[REGEXPTITLE:File Upload]";
			 if(x.winWait(upload,"",10)){
				 x.winActivate(upload);
				 x.sleep(3000);
				 x.controlFocus(upload,"","[CLASS:Edit;INSTANCE:1]");
				 x.send(filePath);
				 x.sleep(3000);
				 x.controlClick(upload, "", "[TEXT:&Open]");
				 x.sleep(3000);
			 }else{
				 logger.error("Timeout exceeded to upload the File: " + filePath);
			 }
		 }
	 }

	 /**
	  * @return current page url
	  */
	 public String getCurrentPageUrl() {
		 return browser.fetch("document.location.href");
	 }

	 /**
	  * Simulates a window close event by pressing ctrl + w
          * @param regexWinTitle
	  */
	 public void closeWindow(String regexWinTitle) {
		 loadAutoIT();
		 //Loading AutoITX Library
		 AutoItX x = new AutoItX();
		 String win = "[REGEXPTITLE:"+regexWinTitle+"]";
		 if(x.winWait(win,"",10)){
			 x.winActivate(win);
			 x.sleep(3000);
			 x.controlSend(win, "", "", "{CTRLDOWN}{w}{CTRLUP}");
			 logger.debug(" >>>Closing window >> send: ctrl + w");
			 x.sleep(1000);
		 }
	 }

	 /**
	  * Simulates an event to copy current window's url by first focusing to address bar(by pressing F6)
	  * and then copying the url(by pressing ctrl + c)
	  * @param regexWinTitle Window's title or regex for title whose url is to be copied
	  * @return current page url
	  */
	 public String copyPageUrl(String regexWinTitle) {
		 String url= "";
		 loadAutoIT();
		 //Loading AutoITX Library
		 AutoItX x = new AutoItX();
		 String win = "[REGEXPTITLE:"+regexWinTitle+"]";
		 if(x.winWait(win,"",10)){
			 x.winActivate(win);
			 x.sleep(3000);
			 x.controlSend(win, "", "", "{F6}");
			 logger.debug(" >>>Focus on address bar >> send: {F6}");
			 x.sleep(2000);
			 x.controlSend(win, "", "", "{CTRLDOWN}{c}{CTRLUP}");
			 x.sleep(2000);
			 logger.debug(" >>>Copy url >> send: ctrl + c}");
			 url = x.clipGet();
			 x.sleep(1000);
		 }
		 logger.error("Unable to copy page url");
		 return url;
	 }

	 /**
	  * Loads jacob and auto it dlls depending on bit mode of current jre. Loads 32 bit dlls
	  * if current jre is 32 bit, loads 64 bit dlls if current jre is 64 bits.
	  */
	 private void loadAutoIT() {
		 try {
			 File jacobDLL, autoITXDLL;
			 // Get the bit mode of jre using property "sun.arch.data.model". We might not be using Sun's java.
			 // In that case, use "os.arch" to determine if jre is 32 bit or 64 bit
			 int jreBitMode = ((System.getProperty("sun.arch.data.model").equals("32"))
                                           || (
                                                System.getProperty("os.arch") != null
                                                && System.getProperty("os.arch").contains("86"))) ? 32 : 64; 
			 if(jreBitMode == 64){
				 logger.debug(" >>>Loading x64 libs");
				 jacobDLL = new File("lib", "jacob-1.17-x64.dll");
				 autoITXDLL = new File("lib", "AutoItX3_x64.dll");
			 }else{
				 logger.debug(" >>>Loading x86 libs");
				 jacobDLL = new File("lib", "jacob-1.17-x86.dll");
				 autoITXDLL = new File("lib", "AutoItX3.dll");
			 }
			 //Registering the JACOB and AutoITX DLL Libraries
			 System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDLL.getAbsolutePath());
			 Process p = Runtime.getRuntime().exec("regsvr32.exe /s " + autoITXDLL.getAbsolutePath());
			 p.waitFor();
		 } catch (Exception e) {
			 logger.error(e.fillInStackTrace());
		 }
	 }

	 /**
	  * Used to manage the download file dialog displayed by Firefox and Chrome
	  * @param downloadLink Element Stub referred to the download link that we want to do a mouse clicked action
	  * @param downloadFolder Folder Path Where we are going to download the file 
          * @param fileExtension Extension of the specific file than we want to download
	  * @param downloadTimeout timeout in milliseconds  before to declare the download action as Fail
	  * @return the Absolute path of the downloaded File using the default filename
	  */
	 public String downloadFile(ElementStub downloadLink,
			 String downloadFolder, String fileExtension, int downloadTimeout) {
		 return downloadFile(downloadLink, downloadFolder, "", fileExtension, downloadTimeout);
	 }

	 /**
	  * Used to manage the download file dialog displayed by Firefox and Chrome
	  * @param downloadLink Element Stub referred to the download link that we want to do a mouse clicked action
	  * @param downloadFolder Folder Path Where we are going to download the file
	  * @param fileName name to be assigned to the downloaded file 
	  * @param fileExtension file extension to be assigned to the downloaded file 
	  * @param downloadTimeout timeout in milliseconds  before to declare the download action as Fail
	  * @return the Absolute path of the downloaded File
	  */
	 public String downloadFile(ElementStub downloadLink, String downloadFolder,
			 String fileName, String fileExtension, int downloadTimeout){
		 //Loading Jacob and AutoITX3 DLL Libraries
		 loadAutoIT();
		 boolean isFirefox = isFirefox();
		 boolean isChrome = isChrome();
		 super.click(downloadLink);
		 //Loading AutoITX Library
		 AutoItX x = new AutoItX();
		 //Common variables
		 String overrideHead = "Confirm Save As";
		 downloadFolder = downloadFolder.lastIndexOf("\\")==downloadFolder.length()-1?
				 downloadFolder.substring(0, downloadFolder.length()-1):
				 downloadFolder;
				 logger.debug(" >>>downloadFolder: " + downloadFolder);
				 // Assign default download location to file path. Not confident of filename
				 String filePath = System.getProperty("user.home") + "\\Downloads";
				 if (isFirefox){
					 String opening = "[REGEXPTITLE:Opening .*]";
					 String save = "[REGEXPTITLE:Enter name .*]";
					 if(x.winWait(opening,"",10)){
						 x.winActivate(opening);
						 x.sleep(3000);
						 x.controlSend(opening, "", "", "{ALTDOWN}{s}{ALTUP}");
						 x.sleep(3000);
						 logger.debug(" >>>send: {ALT} + s");
						 x.controlSend(opening, "", "", "{ENTER}");
						 x.sleep(3000);
						 logger.debug(" >>>send: {ENTER}");
					 }
					 if(x.winWait(save,"",20)){
						 x.winActivate(save);
						 x.sleep(3000);
						 String defaultFileName = x.controlGetText(save, "", "[CLASS:Edit; INSTANCE:1]");
						 x.sleep(3000);
						 logger.debug(" >>>default File Name: " + defaultFileName);
						 fileName = (!fileName.equals("")?fileName:defaultFileName.lastIndexOf(".")==-1?
								 defaultFileName:
								 defaultFileName.substring(0, defaultFileName.lastIndexOf(".")))
								 + "." + fileExtension;
						 filePath = downloadFolder + System.getProperty("file.separator") + fileName;
						 
						 x.controlFocus(save,"","[CLASS:Edit;INSTANCE:1]");
						 x.send(filePath);
						 logger.debug(" >>>File path: " + filePath);
						 x.sleep(500);
						 
						 x.controlClick(save,"","[TEXT:&Save]");
						 logger.debug(" >>>send: {ALT} + s");
					 }else{
						 logger.error("Timeout exceeded to download the File: " + fileName);
					 }
				 }else if (isChrome){
					 String save = "Save As";
					 if(x.winWait(save,"",10)){
						 x.winActivate(save);
						 x.sleep(3000);
						 String defaultFileName = x.controlGetText(save, "", "[CLASS:Edit; INSTANCE:1]");
						 x.sleep(3000);
						 logger.debug(" >>>default File Name: " + defaultFileName);
						 fileName = !fileName.equals("")?fileName:defaultFileName.lastIndexOf(".")==-1?
								 defaultFileName:
								 defaultFileName.substring(0, defaultFileName.lastIndexOf("."))
								 + "." + fileExtension;
						 filePath = downloadFolder + System.getProperty("file.separator") + fileName;
						 
						 x.controlFocus(save,"","[CLASS:Edit;INSTANCE:1]");
						 x.send(filePath);
						 logger.debug(" >>>File path: " + filePath);
						 x.sleep(500);
						 
						 x.controlClick(save,"","[TEXT:&Save]");
						 logger.debug(" >>>send: {ALT} + s");
					 }else{
						 logger.error("Timeout exceeded to download the File: " + fileName);
					 }
				 }
				 if(x.winWait(overrideHead,"",3)){
					 x.winActivate(overrideHead);
					 x.sleep(3000);
					 x.send("!y",false);
					 x.sleep(3000);
					 logger.debug(" >>>send: {ALT} + y");
				 }
				 //Waiting until the file has been downloaded
				 if(!waitUnlockFile(filePath, downloadTimeout)){
					 logger.error("Timeout exceeded to download the File: " + fileName);
				 }
				 x.sleep(5000);
				 return filePath;
	 }
	 /*
	  * Helper function that waits until the download process finished unlocking the downloaded document
	  */
	 private boolean waitUnlockFile(String filePath, int downloadTimeout){
		 boolean locked = true;
		 do{
			 try{
				 // Get a file channel for the file
				 File file = new File(filePath);
				 RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
				 FileChannel channel =  randomAccessFile.getChannel();
				 // Use the file channel to create a lock on the file.
				 // This method blocks until it can retrieve the lock.
				 FileLock lock = null;
				 // Try acquiring the lock without blocking. This method returns
				 // null or throws an exception if the file is already locked.
				 try{
					 lock = channel.tryLock();
					 locked = false;
				 }catch (OverlappingFileLockException e){
				 }finally{
					 // Remember to release the lock
					 lock.release();
					 // Close the file
					 channel.close();
					 randomAccessFile.close();
				 }
			 }catch (Exception e){}
			 finally{
				 //wait by 1 second before to verify the download again
				 this.waitFor(1000);
				 downloadTimeout -= 1000;
			 }
		 }while(locked & downloadTimeout > 0);
		 return !locked;
	 }

	 public boolean cancelSaveDialog() {
		 // Loading Jacob and AutoITX3 DLL Libraries
		 loadAutoIT();
		 boolean close = false;
		 AutoItX x = new AutoItX();
		 x.sleep(3000);
		 String opening = "[REGEXPTITLE:Opening .*]";
		 if(x.winWait(opening,"",10)){
			 x.winActivate(opening);
			 x.sleep(2000);
			 x.controlSend(opening, "", "", "{ALTDOWN}{s}{ALTUP}");
			 x.sleep(2000);
			 // Press tab to focus cancel button
			 logger.debug(" >>>send: {ALT} + s");
			 x.controlSend(opening, "", "", "{TAB}");
			 logger.debug(" >>>send: {TAB}");
			 x.sleep(500);
			 x.controlSend(opening, "", "", "{TAB}");
			 logger.debug(" >>>send: {TAB}");
			 x.sleep(500);
			 x.controlSend(opening, "", "", "{TAB}");
			 logger.debug(" >>>send: {TAB}");
			 x.sleep(500);
			 x.controlSend(opening, "", "", "{TAB}");
			 logger.debug(" >>>send: {TAB}");
			 x.sleep(500);
			 close = x.controlSend(opening, "", "", "{ENTER}");
		 }
		 return close;
	 }

	 /**
	  * Refresh page
	  */
	 public void refresh() {
		 logger.debug("Simulating page refresh event");
		 super.execute("location.reload()");
	 }
}