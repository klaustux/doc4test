package lt.pvz.doc4test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.variable.BulletListVariable;
import pl.jsolve.templ4docx.variable.TableVariable;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variable;
import pl.jsolve.templ4docx.variable.Variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Doc4testApplicationTests {


	@Test
	public void contextLoads() {


		IpcCpc ipc1 = new IpcCpc("A01", "", true);
		IpcCpc ipc2 = new IpcCpc("A02", "", true);
		IpcCpc ipc3 = new IpcCpc("", "XXX", false);

		List<IpcCpc> ipcCpcList = Arrays.asList(ipc1, ipc2, ipc3);
		List<Variable> cpcList = new ArrayList<>();
		List<Variable> ipcList = new ArrayList<>();


		for (IpcCpc ipcCpc : ipcCpcList) {
			if (ipcCpc.isIpc) {
				TextVariable ipc = new TextVariable("${ipc}", ipcCpc.ipc);
				ipcList.add(ipc);
			}
			else {
				TextVariable cpc = new TextVariable("${cpc}", ipcCpc.cpc);
				cpcList.add(cpc);
			}
		}

		Docx docx = new Docx("/tmp/tmp.docx");
		Variables var = new Variables();


		List<Variable> kairysStulpelis = new ArrayList<>();
		List<Variable> desinysStulpelis = new ArrayList<>();
		kairysStulpelis.add(new BulletListVariable("${ipc}", ipcList));
		desinysStulpelis.add(new BulletListVariable("${cpc}", cpcList));


		TableVariable tableVariable = new TableVariable();
		tableVariable.addVariable(kairysStulpelis);
		tableVariable.addVariable(desinysStulpelis);


		var.addTableVariable(tableVariable);
		docx.fillTemplate(var);
		docx.save("/tmp/filledTable.docx");
	}

	class IpcCpc {
		public String ipc;
		public String cpc;
		public boolean isIpc;

		public IpcCpc(String ipc, String cpc, boolean isIpc) {
			this.ipc = ipc;
			this.cpc = cpc;
			this.isIpc = isIpc;
		}
	}

}


