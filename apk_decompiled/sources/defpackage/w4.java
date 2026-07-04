package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class w4 implements kv {
    public final /* synthetic */ int e;

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        jf jfVar;
        boolean a;
        int i = this.e;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                ((Integer) obj2).getClass();
                x4.a((bw) obj, d20.O(1));
                return x31Var;
            case 1:
                String str = (String) obj;
                wj wjVar = (wj) obj2;
                str.getClass();
                wjVar.getClass();
                if (str.length() == 0) {
                    return wjVar.toString();
                }
                return str + ", " + wjVar;
            case 2:
                bw bwVar = (bw) obj;
                int intValue = ((Integer) obj2).intValue();
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    n30.c(bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
            case 3:
                yj yjVar = (yj) obj;
                wj wjVar2 = (wj) obj2;
                yjVar.getClass();
                wjVar2.getClass();
                yj s = yjVar.s(wjVar2.getKey());
                cr crVar = cr.e;
                if (s != crVar) {
                    x1 x1Var = x1.A;
                    ak akVar = (ak) s.j(x1Var);
                    if (akVar == null) {
                        jfVar = new jf(wjVar2, s);
                    } else {
                        yj s2 = s.s(x1Var);
                        if (s2 == crVar) {
                            return new jf(akVar, wjVar2);
                        }
                        jfVar = new jf(akVar, new jf(wjVar2, s2));
                    }
                    return jfVar;
                }
                return wjVar2;
            case 4:
                return ((yj) obj).i((wj) obj2);
            case 5:
                return ((yj) obj).i((wj) obj2);
            case 6:
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return bool;
            case 7:
                up upVar = (up) obj;
                gv gvVar = (gv) obj2;
                upVar.getClass();
                gvVar.getClass();
                gvVar.e(upVar);
                return x31Var;
            case 8:
                m70 m70Var = (m70) obj2;
                return jc0.w(Integer.valueOf(((fk0) m70Var.e.b).g()), Integer.valueOf(((fk0) m70Var.e.c).g()));
            case 9:
                Map d = ((p70) obj2).d();
                if (d.isEmpty()) {
                    return null;
                }
                return d;
            case 10:
                return Integer.valueOf(((Integer) obj).intValue() + 1);
            case 11:
                ds0 ds0Var = (ds0) obj2;
                Map map = ds0Var.e;
                ve0 ve0Var = ds0Var.f;
                Object[] objArr = ve0Var.b;
                Object[] objArr2 = ve0Var.c;
                long[] jArr = ve0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j = jArr[i2];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8 - ((~(i2 - length)) >>> 31);
                            for (int i4 = 0; i4 < i3; i4++) {
                                if ((255 & j) < 128) {
                                    int i5 = (i2 << 3) + i4;
                                    Object obj3 = objArr[i5];
                                    Map d2 = ((es0) objArr2[i5]).d();
                                    if (d2.isEmpty()) {
                                        map.remove(obj3);
                                    } else {
                                        map.put(obj3, d2);
                                    }
                                }
                                j >>= 8;
                            }
                            if (i3 != 8) {
                            }
                        }
                        if (i2 != length) {
                            i2++;
                        }
                    }
                }
                if (map.isEmpty()) {
                    return null;
                }
                return map;
            case 12:
                return obj2;
            case 13:
                l7 l7Var = (l7) obj2;
                return jc0.h(l7Var.f, xs0.a(l7Var.e, xs0.a, (bs0) obj));
            case 14:
                return Integer.valueOf(((w01) obj2).a);
            case 15:
                b11 b11Var = (b11) obj2;
                return jc0.h(Float.valueOf(b11Var.a), Float.valueOf(b11Var.b));
            case 16:
                bs0 bs0Var = (bs0) obj;
                c11 c11Var = (c11) obj2;
                t11 t11Var = new t11(c11Var.a);
                ws0 ws0Var = xs0.v;
                return jc0.h(xs0.a(t11Var, ws0Var, bs0Var), xs0.a(new t11(c11Var.b), ws0Var, bs0Var));
            case 17:
                return Integer.valueOf(((nu) obj2).e);
            case 18:
                d90 d90Var = (d90) obj2;
                return jc0.h(d90Var.a, xs0.a(d90Var.b, xs0.i, (bs0) obj));
            case 19:
                return Float.valueOf(((t9) obj2).a);
            case 20:
                bs0 bs0Var2 = (bs0) obj;
                List list = (List) obj2;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i6 = 0; i6 < size; i6++) {
                    arrayList.add(xs0.a((k7) list.get(i6), xs0.b, bs0Var2));
                }
                return arrayList;
            case 21:
                m11 m11Var = (m11) obj2;
                return jc0.h(Integer.valueOf((int) (m11Var.a >> 32)), Integer.valueOf((int) (m11Var.a & 4294967295L)));
            case 22:
                bs0 bs0Var3 = (bs0) obj;
                tv0 tv0Var = (tv0) obj2;
                return jc0.h(xs0.a(new se(tv0Var.a), xs0.p, bs0Var3), xs0.a(new ch0(tv0Var.b), xs0.x, bs0Var3), Float.valueOf(tv0Var.c));
            case 23:
                return Integer.valueOf(((t01) obj2).a);
            case 24:
                return Integer.valueOf(((y01) obj2).a);
            case 25:
                return Integer.valueOf(((yy) obj2).a);
            case 26:
                return Integer.valueOf(((lu) obj2).a);
            case 27:
                return Integer.valueOf(((mu) obj2).a);
            case 28:
                bs0 bs0Var4 = (bs0) obj;
                t11 t11Var2 = (t11) obj2;
                long j2 = t11.c;
                if (t11Var2 == null) {
                    a = false;
                } else {
                    a = t11.a(t11Var2.a, j2);
                }
                if (a) {
                    return Boolean.FALSE;
                }
                return jc0.h(Float.valueOf(t11.c(t11Var2.a)), xs0.a(new u11(t11.b(t11Var2.a)), xs0.w, bs0Var4));
            default:
                c90 c90Var = (c90) obj2;
                return jc0.h(c90Var.a, xs0.a(c90Var.b, xs0.i, (bs0) obj));
        }
    }

    public /* synthetic */ w4(int i, byte b) {
        this.e = i;
    }
}
