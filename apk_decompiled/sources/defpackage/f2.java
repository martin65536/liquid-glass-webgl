package defpackage;

import android.os.Build;
import android.os.Bundle;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class f2 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Serializable h;
    public final /* synthetic */ Object i;
    public final /* synthetic */ Object j;

    public /* synthetic */ f2(Object obj, Object obj2, Serializable serializable, Object obj3, Object obj4, int i) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
        this.h = serializable;
        this.i = obj3;
        this.j = obj4;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        Object parcelable;
        int i = this.e;
        boolean z = true;
        Object obj2 = this.j;
        Object obj3 = this.i;
        Serializable serializable = this.h;
        Object obj4 = this.g;
        Object obj5 = this.f;
        int i2 = 0;
        switch (i) {
            case 0:
                b2 b2Var = (b2) obj5;
                ag agVar = (ag) obj4;
                String str = (String) serializable;
                dt0 dt0Var = (dt0) obj3;
                g2 g2Var = new g2((af0) obj2);
                af0 af0Var = (af0) g2Var.a;
                Bundle bundle = agVar.g;
                LinkedHashMap linkedHashMap = agVar.a;
                LinkedHashMap linkedHashMap2 = agVar.f;
                str.getClass();
                LinkedHashMap linkedHashMap3 = agVar.b;
                if (((Integer) linkedHashMap3.get(str)) == null) {
                    c2 c2Var = new c2(0);
                    Iterator it = new ri(new cs(c2Var, new l(16, c2Var), 1)).iterator();
                    while (it.hasNext()) {
                        Number number = (Number) it.next();
                        if (!linkedHashMap.containsKey(Integer.valueOf(number.intValue()))) {
                            int intValue = number.intValue();
                            linkedHashMap.put(Integer.valueOf(intValue), str);
                            linkedHashMap3.put(str, Integer.valueOf(intValue));
                        }
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                agVar.e.put(str, new d2(g2Var, dt0Var));
                if (linkedHashMap2.containsKey(str)) {
                    Object obj6 = linkedHashMap2.get(str);
                    linkedHashMap2.remove(str);
                    ((gv) af0Var.getValue()).e(obj6);
                }
                if (Build.VERSION.SDK_INT >= 34) {
                    parcelable = g1.c(bundle, str);
                } else {
                    parcelable = bundle.getParcelable(str);
                    if (!w1.class.isInstance(parcelable)) {
                        parcelable = null;
                    }
                }
                w1 w1Var = (w1) parcelable;
                if (w1Var != null) {
                    bundle.remove(str);
                    ((gv) af0Var.getValue()).e(dt0Var.i(w1Var.f, w1Var.e));
                }
                b2Var.a = new e2(agVar, str, dt0Var);
                return new h2(i2, b2Var);
            default:
                ud0 ud0Var = (ud0) obj5;
                ep0 ep0Var = (ep0) obj4;
                bp0 bp0Var = (bp0) serializable;
                hu0 hu0Var = (hu0) obj3;
                ap0 ap0Var = (ap0) obj2;
                float floatValue = ((Float) obj).floatValue();
                qd0 g = ud0.g(ud0Var.g);
                if (g != null) {
                    c4 c4Var = ud0Var.e;
                    long j = g.b;
                    long j2 = g.a;
                    ((x41) c4Var.f).a(j, Float.intBitsToFloat((int) (j2 >> 32)));
                    ((x41) c4Var.g).a(j, Float.intBitsToFloat((int) (j2 & 4294967295L)));
                    qd0 a = ((qd0) ep0Var.e).a(g);
                    ep0Var.e = a;
                    bp0Var.e = hu0Var.i(hu0Var.e(a.a));
                    ap0Var.e = !d20.c(r14 - floatValue);
                }
                if (g == null) {
                    z = false;
                }
                return Boolean.valueOf(z);
        }
    }
}
