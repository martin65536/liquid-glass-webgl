package defpackage;

import android.content.Context;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class pb implements gv {
    public final /* synthetic */ int e;

    public /* synthetic */ pb(int i, h70 h70Var) {
        this.e = 11;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        ux0 ux0Var;
        ux0 ux0Var2;
        ux0 ux0Var3;
        List list;
        t11 t11Var;
        Integer num;
        se seVar;
        ch0 ch0Var;
        String str;
        ux0 ux0Var4 = null;
        r3 = null;
        j11 j11Var = null;
        Float f = null;
        Integer num2 = null;
        t11 t11Var2 = null;
        String str2 = null;
        ux0Var4 = null;
        switch (this.e) {
            case 0:
                qy0 qy0Var = p4.b;
                ll0 ll0Var = (ll0) ((zh) obj);
                ll0Var.getClass();
                if (!((Context) jc0.A(ll0Var, qy0Var)).getPackageManager().hasSystemFeature("android.software.leanback")) {
                    ob.a.getClass();
                    return nb.c;
                }
                return qb.b;
            case 1:
                wj wjVar = (wj) obj;
                if (!(wjVar instanceof ak)) {
                    return null;
                }
                return (ak) wjVar;
            case 2:
                return Boolean.TRUE;
            case 3:
                return x31.a;
            case 4:
                return x31.a;
            case 5:
                synchronized (cx0.c) {
                    List list2 = cx0.i;
                    int size = list2.size();
                    for (int i = 0; i < size; i++) {
                        ((gv) list2.get(i)).e(obj);
                    }
                }
                return x31.a;
            case 6:
                return x31.a;
            case 7:
                ((byte[]) obj).getClass();
                throw new IllegalStateException("Android platform doesn't support SVG format.");
            case 8:
                b50 b50Var = (b50) obj;
                b50Var.getClass();
                b50Var.r();
                return x31.a;
            case 9:
                ((Integer) obj).getClass();
                return null;
            case 10:
                List list3 = (List) obj;
                return new m70(((Number) list3.get(0)).intValue(), ((Number) list3.get(1)).intValue());
            case 11:
                return x31.a;
            case 12:
                yj0 yj0Var = (yj0) obj;
                return "[" + yj0Var.b + ", " + yj0Var.c + ')';
            case 13:
                n9 n9Var = ((hg0) obj).a;
                if (n9Var != null) {
                    n9Var.a();
                }
                return x31.a;
            case 14:
                ((Long) obj).getClass();
                return x31.a;
            case 15:
                zh zhVar = (zh) obj;
                int i2 = q5.a;
                qy0 qy0Var2 = p4.b;
                ll0 ll0Var2 = (ll0) zhVar;
                ll0Var2.getClass();
                Context context = (Context) jc0.A(ll0Var2, qy0Var2);
                ll0 ll0Var3 = (ll0) zhVar;
                mm mmVar = (mm) jc0.A(ll0Var3, fi.h);
                ij0 ij0Var = (ij0) jc0.A(ll0Var3, jj0.a);
                if (ij0Var == null) {
                    return null;
                }
                return new f5(context, mmVar, ij0Var.a, ij0Var.b);
            case 16:
                fq0 fq0Var = (fq0) obj;
                fq0Var.getClass();
                return fq0Var.a;
            case 17:
                return new ds0((Map) obj);
            case 18:
                return obj;
            case 19:
                obj.getClass();
                List list4 = (List) obj;
                Object obj2 = list4.get(0);
                gv gvVar = (gv) xs0.h.g;
                Boolean bool = Boolean.FALSE;
                if (o20.e(obj2, bool) || obj2 == null) {
                    ux0Var = null;
                } else {
                    ux0Var = (ux0) gvVar.e(obj2);
                }
                Object obj3 = list4.get(1);
                if (o20.e(obj3, bool) || obj3 == null) {
                    ux0Var2 = null;
                } else {
                    ux0Var2 = (ux0) gvVar.e(obj3);
                }
                Object obj4 = list4.get(2);
                if (o20.e(obj4, bool) || obj4 == null) {
                    ux0Var3 = null;
                } else {
                    ux0Var3 = (ux0) gvVar.e(obj4);
                }
                Object obj5 = list4.get(3);
                if (!o20.e(obj5, bool) && obj5 != null) {
                    ux0Var4 = (ux0) gvVar.e(obj5);
                }
                return new j11(ux0Var, ux0Var2, ux0Var3, ux0Var4);
            case 20:
                obj.getClass();
                List list5 = (List) obj;
                Object obj6 = list5.get(1);
                c4 c4Var = xs0.a;
                if (o20.e(obj6, Boolean.FALSE) || obj6 == null) {
                    list = null;
                } else {
                    list = (List) ((gv) c4Var.g).e(obj6);
                }
                Object obj7 = list5.get(0);
                if (obj7 != null) {
                    str2 = (String) obj7;
                }
                str2.getClass();
                return new l7(list, str2);
            case 21:
                obj.getClass();
                return new w01(((Integer) obj).intValue());
            case 22:
                obj.getClass();
                List list6 = (List) obj;
                return new b11(((Number) list6.get(0)).floatValue(), ((Number) list6.get(1)).floatValue());
            case 23:
                obj.getClass();
                List list7 = (List) obj;
                Object obj8 = list7.get(0);
                u11[] u11VarArr = t11.b;
                gv gvVar2 = xs0.v.f;
                Boolean bool2 = Boolean.FALSE;
                o20.e(obj8, bool2);
                if (obj8 != null) {
                    t11Var = (t11) gvVar2.e(obj8);
                } else {
                    t11Var = null;
                }
                t11Var.getClass();
                long j = t11Var.a;
                Object obj9 = list7.get(1);
                o20.e(obj9, bool2);
                if (obj9 != null) {
                    t11Var2 = (t11) gvVar2.e(obj9);
                }
                t11Var2.getClass();
                return new c11(j, t11Var2.a);
            case 24:
                obj.getClass();
                return new nu(((Integer) obj).intValue());
            case 25:
                obj.getClass();
                return new t9(((Float) obj).floatValue());
            case 26:
                obj.getClass();
                List list8 = (List) obj;
                Object obj10 = list8.get(0);
                if (obj10 != null) {
                    num = (Integer) obj10;
                } else {
                    num = null;
                }
                num.getClass();
                int intValue = num.intValue();
                Object obj11 = list8.get(1);
                if (obj11 != null) {
                    num2 = (Integer) obj11;
                }
                num2.getClass();
                return new m11(n30.d(intValue, num2.intValue()));
            case 27:
                obj.getClass();
                List list9 = (List) obj;
                Object obj12 = list9.get(0);
                int i3 = se.h;
                Boolean bool3 = Boolean.FALSE;
                o20.e(obj12, bool3);
                if (obj12 != null) {
                    if (o20.e(obj12, Boolean.FALSE)) {
                        seVar = new se(se.g);
                    } else {
                        seVar = new se(f31.e(((Integer) obj12).intValue()));
                    }
                } else {
                    seVar = null;
                }
                seVar.getClass();
                long j2 = seVar.a;
                Object obj13 = list9.get(1);
                ws0 ws0Var = xs0.x;
                o20.e(obj13, bool3);
                if (obj13 != null) {
                    ch0Var = (ch0) ws0Var.f.e(obj13);
                } else {
                    ch0Var = null;
                }
                ch0Var.getClass();
                long j3 = ch0Var.a;
                Object obj14 = list9.get(2);
                if (obj14 != null) {
                    f = (Float) obj14;
                }
                f.getClass();
                return new tv0(j2, j3, f.floatValue());
            case 28:
                obj.getClass();
                return new t01(((Integer) obj).intValue());
            default:
                obj.getClass();
                List list10 = (List) obj;
                Object obj15 = list10.get(0);
                if (obj15 != null) {
                    str = (String) obj15;
                } else {
                    str = null;
                }
                str.getClass();
                Object obj16 = list10.get(1);
                c4 c4Var2 = xs0.i;
                if (!o20.e(obj16, Boolean.FALSE) && obj16 != null) {
                    j11Var = (j11) ((gv) c4Var2.g).e(obj16);
                }
                return new d90(str, j11Var);
        }
    }

    public /* synthetic */ pb(int i) {
        this.e = i;
    }
}
