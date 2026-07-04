package defpackage;

import android.os.Trace;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class us0 implements kv {
    public final /* synthetic */ int e;

    public /* synthetic */ us0(int i) {
        this.e = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean c;
        n7 n7Var;
        Object a;
        int i;
        Integer num = null;
        switch (this.e) {
            case 0:
                long j = ((u11) obj2).a;
                if (u11.a(j, 8589934592L)) {
                    return 0;
                }
                if (u11.a(j, 4294967296L)) {
                    return 1;
                }
                return Boolean.FALSE;
            case 1:
                ch0 ch0Var = (ch0) obj2;
                if (ch0Var == null) {
                    c = false;
                } else {
                    c = ch0.c(ch0Var.a, 9205357640488583168L);
                }
                if (c) {
                    return Boolean.FALSE;
                }
                return jc0.h(Float.valueOf(Float.intBitsToFloat((int) (ch0Var.a >> 32))), Float.valueOf(Float.intBitsToFloat((int) (ch0Var.a & 4294967295L))));
            case 2:
                bs0 bs0Var = (bs0) obj;
                k7 k7Var = (k7) obj2;
                Object obj3 = k7Var.a;
                if (obj3 instanceof ck0) {
                    n7Var = n7.e;
                } else if (obj3 instanceof ux0) {
                    n7Var = n7.f;
                } else if (obj3 instanceof y41) {
                    n7Var = n7.g;
                } else if (obj3 instanceof g41) {
                    n7Var = n7.h;
                } else if (obj3 instanceof d90) {
                    n7Var = n7.i;
                } else if (obj3 instanceof c90) {
                    n7Var = n7.j;
                } else if (obj3 instanceof ty0) {
                    n7Var = n7.k;
                } else {
                    throw new UnsupportedOperationException();
                }
                switch (n7Var.ordinal()) {
                    case 0:
                        obj3.getClass();
                        a = xs0.a((ck0) obj3, xs0.g, bs0Var);
                        break;
                    case 1:
                        obj3.getClass();
                        a = xs0.a((ux0) obj3, xs0.h, bs0Var);
                        break;
                    case 2:
                        obj3.getClass();
                        a = xs0.a((y41) obj3, xs0.c, bs0Var);
                        break;
                    case 3:
                        obj3.getClass();
                        a = xs0.a((g41) obj3, xs0.d, bs0Var);
                        break;
                    case 4:
                        obj3.getClass();
                        a = xs0.a((d90) obj3, xs0.e, bs0Var);
                        break;
                    case 5:
                        obj3.getClass();
                        a = xs0.a((c90) obj3, xs0.f, bs0Var);
                        break;
                    case 6:
                        obj3.getClass();
                        a = ((ty0) obj3).a;
                        break;
                    default:
                        v7.k();
                        return null;
                }
                return jc0.h(n7Var, a, Integer.valueOf(k7Var.b), Integer.valueOf(k7Var.c), k7Var.d);
            case 3:
                bs0 bs0Var2 = (bs0) obj;
                List list = ((ua0) obj2).e;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.add(xs0.a((ta0) list.get(i2), xs0.z, bs0Var2));
                }
                return arrayList;
            case 4:
                return ((ta0) obj2).a.toLanguageTag();
            case 5:
                bs0 bs0Var3 = (bs0) obj;
                x80 x80Var = (x80) obj2;
                return jc0.h(xs0.a(new u80(x80Var.a), xs0.B, bs0Var3), xs0.a(new w80(x80Var.b), xs0.C, bs0Var3), xs0.a(new v80(x80Var.c), xs0.D, bs0Var3));
            case 6:
                return Float.valueOf(((u80) obj2).a);
            case 7:
                return Integer.valueOf(((w80) obj2).a);
            case 8:
                return Integer.valueOf(((v80) obj2).a);
            case 9:
                return ((y41) obj2).a;
            case 10:
                bs0 bs0Var4 = (bs0) obj;
                ck0 ck0Var = (ck0) obj2;
                Object a2 = xs0.a(new t01(ck0Var.a), xs0.q, bs0Var4);
                Object a3 = xs0.a(new y01(ck0Var.b), xs0.r, bs0Var4);
                Object a4 = xs0.a(new t11(ck0Var.c), xs0.v, bs0Var4);
                c11 c11Var = ck0Var.d;
                c11 c11Var2 = c11.c;
                Object a5 = xs0.a(c11Var, xs0.l, bs0Var4);
                Object a6 = xs0.a(ck0Var.e, k81.g, bs0Var4);
                x80 x80Var2 = ck0Var.f;
                x80 x80Var3 = x80.d;
                return jc0.h(a2, a3, a4, a5, a6, xs0.a(x80Var2, xs0.A, bs0Var4), xs0.a(new s80(ck0Var.g), k81.i, bs0Var4), xs0.a(new yy(ck0Var.h), xs0.s, bs0Var4), xs0.a(ck0Var.i, k81.j, bs0Var4));
            case 11:
                return ((g41) obj2).a;
            case 12:
                bs0 bs0Var5 = (bs0) obj;
                ux0 ux0Var = (ux0) obj2;
                se seVar = new se(ux0Var.a.a());
                ws0 ws0Var = xs0.p;
                Object a7 = xs0.a(seVar, ws0Var, bs0Var5);
                t11 t11Var = new t11(ux0Var.b);
                ws0 ws0Var2 = xs0.v;
                Object a8 = xs0.a(t11Var, ws0Var2, bs0Var5);
                nu nuVar = ux0Var.c;
                nu nuVar2 = nu.f;
                Object a9 = xs0.a(nuVar, xs0.m, bs0Var5);
                Object a10 = xs0.a(ux0Var.d, xs0.t, bs0Var5);
                Object a11 = xs0.a(ux0Var.e, xs0.u, bs0Var5);
                String str = ux0Var.g;
                Object a12 = xs0.a(new t11(ux0Var.h), ws0Var2, bs0Var5);
                Object a13 = xs0.a(ux0Var.i, xs0.n, bs0Var5);
                Object a14 = xs0.a(ux0Var.j, xs0.k, bs0Var5);
                ua0 ua0Var = ux0Var.k;
                ua0 ua0Var2 = ua0.g;
                Object a15 = xs0.a(ua0Var, xs0.y, bs0Var5);
                Object a16 = xs0.a(new se(ux0Var.l), ws0Var, bs0Var5);
                Object a17 = xs0.a(ux0Var.m, xs0.j, bs0Var5);
                tv0 tv0Var = ux0Var.n;
                tv0 tv0Var2 = tv0.d;
                return jc0.h(a7, a8, a9, a10, a11, -1, str, a12, a13, a14, a15, a16, a17, xs0.a(tv0Var, xs0.o, bs0Var5));
            case 13:
                bs0 bs0Var6 = (bs0) obj;
                j11 j11Var = (j11) obj2;
                ux0 ux0Var2 = j11Var.a;
                c4 c4Var = xs0.h;
                return jc0.h(xs0.a(ux0Var2, c4Var, bs0Var6), xs0.a(j11Var.b, c4Var, bs0Var6), xs0.a(j11Var.c, c4Var, bs0Var6), xs0.a(j11Var.d, c4Var, bs0Var6));
            case 14:
                mm0 mm0Var = (mm0) obj2;
                Boolean valueOf = Boolean.valueOf(mm0Var.a);
                c4 c4Var2 = xs0.a;
                return jc0.h(valueOf, xs0.a(new yq(mm0Var.b), k81.h, (bs0) obj));
            case 15:
                return Integer.valueOf(((yq) obj2).a);
            case 16:
                return Integer.valueOf(((s80) obj2).a);
            case 17:
                l11 l11Var = (l11) obj2;
                return jc0.h(xs0.a(new k11(l11Var.a), k81.k, (bs0) obj), Boolean.valueOf(l11Var.b));
            case 18:
                return Integer.valueOf(((k11) obj2).a);
            case 19:
                return Integer.valueOf(((nt0) obj2).a.g());
            case 20:
                wj wjVar = (wj) obj2;
                if (!(wjVar instanceof m21)) {
                    return obj;
                }
                if (obj instanceof Integer) {
                    num = (Integer) obj;
                }
                if (num != null) {
                    i = num.intValue();
                } else {
                    i = 1;
                }
                if (i == 0) {
                    return wjVar;
                }
                return Integer.valueOf(i + 1);
            case 21:
                wj wjVar2 = (wj) obj2;
                if (!(wjVar2 instanceof m21)) {
                    return null;
                }
                return (m21) wjVar2;
            default:
                z11 z11Var = (z11) obj;
                wj wjVar3 = (wj) obj2;
                if (wjVar3 instanceof m21) {
                    yj yjVar = z11Var.a;
                    Trace.beginSection(null);
                    Object[] objArr = z11Var.b;
                    int i3 = z11Var.d;
                    objArr[i3] = x31.a;
                    m21[] m21VarArr = z11Var.c;
                    z11Var.d = i3 + 1;
                    m21VarArr[i3] = (m21) wjVar3;
                }
                return z11Var;
        }
    }
}
