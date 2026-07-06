package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fs0 implements es0 {
    public final gv e;
    public final ve0 f;
    public ve0 g;

    public fs0(Map map, gv gvVar) {
        ve0 ve0Var;
        this.e = gvVar;
        if (map != null && !map.isEmpty()) {
            ve0Var = new ve0(map.size());
            for (Map.Entry entry : map.entrySet()) {
                ve0Var.m(entry.getKey(), entry.getValue());
            }
        } else {
            ve0Var = null;
        }
        this.f = ve0Var;
    }

    @Override // defpackage.es0
    public final r7 a(String str, vu vuVar) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!k81.A(str.charAt(i))) {
                ve0 ve0Var = this.g;
                if (ve0Var == null) {
                    long[] jArr = zs0.a;
                    ve0Var = new ve0();
                    this.g = ve0Var;
                }
                Object g = ve0Var.g(str);
                if (g == null) {
                    g = new ArrayList();
                    ve0Var.m(str, g);
                }
                ((List) g).add(vuVar);
                return new r7(ve0Var, str, vuVar, 10);
            }
        }
        v7.m("Registered key is empty or blank");
        return null;
    }

    @Override // defpackage.es0
    public final boolean c(Object obj) {
        return ((Boolean) this.e.e(obj)).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x009a  */
    @Override // defpackage.es0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.Map d() {
        /*
            Method dump skipped, instructions count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fs0.d():java.util.Map");
    }

    @Override // defpackage.es0
    public final Object e(String str) {
        List list;
        ve0 ve0Var = this.f;
        if (ve0Var != null) {
            list = (List) ve0Var.k(str);
        } else {
            list = null;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1 && ve0Var != null) {
            List subList = list.subList(1, list.size());
            int f = ve0Var.f(str);
            if (f < 0) {
                f = ~f;
            }
            Object[] objArr = ve0Var.c;
            Object obj = objArr[f];
            ve0Var.b[f] = str;
            objArr[f] = subList;
        }
        return list.get(0);
    }
}
