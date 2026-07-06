package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bn extends RuntimeException {
    public final fh e;

    public bn(fh fhVar) {
        this.e = fhVar;
        if (!fhVar.b) {
            int[] iArr = {201, 202, 204, 206, 207, 125, -127, 126665345, 200};
            List list = fhVar.a;
            int size = list.size();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                hh hhVar = (hh) list.get(i);
                int i3 = hhVar.a;
                int i4 = 0;
                while (true) {
                    if (i4 < 9) {
                        if (i3 == iArr[i4]) {
                            break;
                        } else {
                            i4++;
                        }
                    } else {
                        i4 = -1;
                        break;
                    }
                }
                if (i4 < 0) {
                    if (hhVar.a == 100) {
                        int i5 = i + 2;
                        if (i5 < size && ((hh) list.get(i5)).a == 1000) {
                            break;
                        } else {
                            re.Q(arrayList);
                        }
                    } else {
                        arrayList.add(hhVar);
                    }
                }
                i = i2;
            }
            int size2 = arrayList.size();
            StackTraceElement[] stackTraceElementArr = new StackTraceElement[size2];
            for (int i6 = 0; i6 < size2; i6++) {
                stackTraceElementArr[i6] = new StackTraceElement("$$compose", "m$" + ((hh) arrayList.get(i6)).a, "SourceFile", 1);
            }
            setStackTrace(stackTraceElementArr);
        }
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        fh fhVar = this.e;
        if (fhVar.b) {
            StringBuilder sb = new StringBuilder("Composition stack when thrown:\n");
            ka0 ka0Var = new ka0(10);
            List list = fhVar.a;
            list.getClass();
            rq0 rq0Var = new rq0(list);
            int a = rq0Var.a();
            for (int i = 0; i < a; i++) {
                ((hh) rq0Var.get(i)).getClass();
            }
            ka0 j = jc0.j(ka0Var);
            j.getClass();
            rq0 rq0Var2 = new rq0(j);
            int a2 = rq0Var2.a();
            for (int i2 = 0; i2 < a2; i2++) {
                String str = (String) rq0Var2.get(i2);
                sb.append("\tat ");
                sb.append(str);
                sb.append('\n');
            }
            return sb.toString();
        }
        return "Composition stack when thrown:";
    }
}
