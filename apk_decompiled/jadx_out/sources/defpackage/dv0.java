package defpackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class dv0 {
    public static final Comparator[] a;
    public static final yu0 b;

    static {
        qt qtVar;
        Comparator[] comparatorArr = new Comparator[2];
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                qtVar = qt.e;
            } else {
                qtVar = qt.c;
            }
            comparatorArr[i] = new cv0(new cv0(qtVar));
        }
        a = comparatorArr;
        b = yu0.i;
    }

    public static final void a(su0 su0Var, ArrayList arrayList, q2 q2Var, q2 q2Var2, he0 he0Var) {
        nu0 nu0Var = su0Var.d;
        Object g = nu0Var.e.g(wu0.m);
        if (g == null) {
            g = Boolean.FALSE;
        }
        boolean booleanValue = ((Boolean) g).booleanValue();
        if ((booleanValue || ((Boolean) q2Var2.e(su0Var)).booleanValue()) && ((Boolean) q2Var.e(su0Var)).booleanValue()) {
            arrayList.add(su0Var);
        }
        if (booleanValue) {
            he0Var.h(su0Var.f, b(su0Var, q2Var, q2Var2, su0.j(7, su0Var)));
            return;
        }
        List j = su0.j(7, su0Var);
        int size = j.size();
        for (int i = 0; i < size; i++) {
            a((su0) j.get(i), arrayList, q2Var, q2Var2, he0Var);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00f3 A[LOOP:1: B:11:0x0046->B:29:0x00f3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00fa A[EDGE_INSN: B:30:0x00fa->B:31:0x00fa BREAK  A[LOOP:1: B:11:0x0046->B:29:0x00f3], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.util.ArrayList b(defpackage.su0 r18, defpackage.q2 r19, defpackage.q2 r20, java.util.List r21) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dv0.b(su0, q2, q2, java.util.List):java.util.ArrayList");
    }
}
