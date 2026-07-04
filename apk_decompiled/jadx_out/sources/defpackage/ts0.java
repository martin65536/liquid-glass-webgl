package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class ts0 implements gv {
    public final /* synthetic */ int e;

    public /* synthetic */ ts0(int i) {
        this.e = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // defpackage.gv
    public final Object e(Object obj) {
        k7 k7Var;
        Float f;
        u11 u11Var;
        Float f2;
        Float f3;
        ta0 ta0Var;
        String str;
        j11 j11Var;
        u80 u80Var;
        w80 w80Var;
        v80 v80Var;
        n7 n7Var;
        Integer num;
        Integer num2;
        String str2;
        ck0 ck0Var;
        k7 k7Var2;
        ux0 ux0Var;
        y41 y41Var;
        g41 g41Var;
        d90 d90Var;
        c90 c90Var;
        String str3;
        String str4;
        String str5;
        t01 t01Var;
        y01 y01Var;
        t11 t11Var;
        c11 c11Var;
        mm0 mm0Var;
        x80 x80Var;
        s80 s80Var;
        yy yyVar;
        l11 l11Var;
        int i;
        int i2;
        se seVar;
        t11 t11Var2;
        nu nuVar;
        lu luVar;
        mu muVar;
        String str6;
        t11 t11Var3;
        t9 t9Var;
        b11 b11Var;
        ua0 ua0Var;
        se seVar2;
        w01 w01Var;
        tv0 tv0Var;
        Boolean bool;
        yq yqVar;
        k11 k11Var;
        Boolean bool2;
        int i3 = this.e;
        x31 x31Var = x31.a;
        int i4 = 0;
        switch (i3) {
            case 0:
                obj.getClass();
                return new y01(((Integer) obj).intValue());
            case 1:
                obj.getClass();
                return new yy(((Integer) obj).intValue());
            case 2:
                obj.getClass();
                List list = (List) obj;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                while (i4 < size) {
                    Object obj2 = list.get(i4);
                    c4 c4Var = xs0.b;
                    if (o20.e(obj2, Boolean.FALSE) || obj2 == null) {
                        k7Var = null;
                    } else {
                        k7Var = (k7) ((gv) c4Var.g).e(obj2);
                    }
                    k7Var.getClass();
                    arrayList.add(k7Var);
                    i4++;
                }
                return arrayList;
            case 3:
                obj.getClass();
                return new lu(((Integer) obj).intValue());
            case 4:
                obj.getClass();
                return new mu(((Integer) obj).intValue());
            case 5:
                Boolean bool3 = Boolean.FALSE;
                if (o20.e(obj, bool3)) {
                    return new t11(t11.c);
                }
                obj.getClass();
                List list2 = (List) obj;
                Object obj3 = list2.get(0);
                if (obj3 != null) {
                    f = (Float) obj3;
                } else {
                    f = null;
                }
                f.getClass();
                float floatValue = f.floatValue();
                Object obj4 = list2.get(1);
                ws0 ws0Var = xs0.w;
                o20.e(obj4, bool3);
                if (obj4 != null) {
                    u11Var = (u11) ws0Var.f.e(obj4);
                } else {
                    u11Var = null;
                }
                u11Var.getClass();
                return new t11(d20.A(u11Var.a, floatValue));
            case 6:
                if (o20.e(obj, 0)) {
                    return new u11(8589934592L);
                }
                if (o20.e(obj, 1)) {
                    return new u11(4294967296L);
                }
                return new u11(0L);
            case 7:
                if (o20.e(obj, Boolean.FALSE)) {
                    return new ch0(9205357640488583168L);
                }
                obj.getClass();
                List list3 = (List) obj;
                Object obj5 = list3.get(0);
                if (obj5 != null) {
                    f2 = (Float) obj5;
                } else {
                    f2 = null;
                }
                f2.getClass();
                float floatValue2 = f2.floatValue();
                Object obj6 = list3.get(1);
                if (obj6 != null) {
                    f3 = (Float) obj6;
                } else {
                    f3 = null;
                }
                f3.getClass();
                float floatValue3 = f3.floatValue();
                return new ch0((Float.floatToRawIntBits(floatValue2) << 32) | (Float.floatToRawIntBits(floatValue3) & 4294967295L));
            case 8:
                obj.getClass();
                List list4 = (List) obj;
                ArrayList arrayList2 = new ArrayList(list4.size());
                int size2 = list4.size();
                while (i4 < size2) {
                    Object obj7 = list4.get(i4);
                    c4 c4Var2 = xs0.z;
                    if (o20.e(obj7, Boolean.FALSE) || obj7 == null) {
                        ta0Var = null;
                    } else {
                        ta0Var = (ta0) ((gv) c4Var2.g).e(obj7);
                    }
                    ta0Var.getClass();
                    arrayList2.add(ta0Var);
                    i4++;
                }
                return new ua0(arrayList2);
            case 9:
                obj.getClass();
                String str7 = (String) obj;
                Locale forLanguageTag = Locale.forLanguageTag(str7);
                if (o20.e(forLanguageTag.toLanguageTag(), "und")) {
                    System.err.println("The language tag " + str7 + " is not well-formed. Locale is resolved to Undetermined. Note that underscore '_' is not a valid subtag delimiter and must be replaced with '-'.");
                }
                return new ta0(forLanguageTag);
            case 10:
                obj.getClass();
                List list5 = (List) obj;
                Object obj8 = list5.get(0);
                if (obj8 != null) {
                    str = (String) obj8;
                } else {
                    str = null;
                }
                str.getClass();
                Object obj9 = list5.get(1);
                c4 c4Var3 = xs0.i;
                if (o20.e(obj9, Boolean.FALSE) || obj9 == null) {
                    j11Var = null;
                } else {
                    j11Var = (j11) ((gv) c4Var3.g).e(obj9);
                }
                return new c90(str, j11Var);
            case 11:
                obj.getClass();
                List list6 = (List) obj;
                Object obj10 = list6.get(0);
                float f4 = u80.b;
                ws0 ws0Var2 = xs0.B;
                Boolean bool4 = Boolean.FALSE;
                o20.e(obj10, bool4);
                if (obj10 != null) {
                    u80Var = (u80) ws0Var2.f.e(obj10);
                } else {
                    u80Var = null;
                }
                u80Var.getClass();
                float f5 = u80Var.a;
                Object obj11 = list6.get(1);
                ws0 ws0Var3 = xs0.C;
                o20.e(obj11, bool4);
                if (obj11 != null) {
                    w80Var = (w80) ws0Var3.f.e(obj11);
                } else {
                    w80Var = null;
                }
                w80Var.getClass();
                int i5 = w80Var.a;
                Object obj12 = list6.get(2);
                ws0 ws0Var4 = xs0.D;
                o20.e(obj12, bool4);
                if (obj12 != null) {
                    v80Var = (v80) ws0Var4.f.e(obj12);
                } else {
                    v80Var = null;
                }
                v80Var.getClass();
                return new x80(f5, i5, v80Var.a);
            case 12:
                obj.getClass();
                float floatValue4 = ((Float) obj).floatValue();
                u80.a(floatValue4);
                return new u80(floatValue4);
            case 13:
                obj.getClass();
                return new w80(((Integer) obj).intValue());
            case 14:
                obj.getClass();
                List list7 = (List) obj;
                Object obj13 = list7.get(0);
                if (obj13 != null) {
                    n7Var = (n7) obj13;
                } else {
                    n7Var = null;
                }
                n7Var.getClass();
                Object obj14 = list7.get(2);
                if (obj14 != null) {
                    num = (Integer) obj14;
                } else {
                    num = null;
                }
                num.getClass();
                int intValue = num.intValue();
                Object obj15 = list7.get(3);
                if (obj15 != null) {
                    num2 = (Integer) obj15;
                } else {
                    num2 = null;
                }
                num2.getClass();
                int intValue2 = num2.intValue();
                Object obj16 = list7.get(4);
                if (obj16 != null) {
                    str2 = (String) obj16;
                } else {
                    str2 = null;
                }
                str2.getClass();
                switch (n7Var.ordinal()) {
                    case 0:
                        Object obj17 = list7.get(1);
                        c4 c4Var4 = xs0.g;
                        if (o20.e(obj17, Boolean.FALSE) || obj17 == null) {
                            ck0Var = null;
                        } else {
                            ck0Var = (ck0) ((gv) c4Var4.g).e(obj17);
                        }
                        ck0Var.getClass();
                        k7Var2 = new k7(ck0Var, intValue, intValue2, str2);
                        break;
                    case 1:
                        Object obj18 = list7.get(1);
                        c4 c4Var5 = xs0.h;
                        if (o20.e(obj18, Boolean.FALSE) || obj18 == null) {
                            ux0Var = null;
                        } else {
                            ux0Var = (ux0) ((gv) c4Var5.g).e(obj18);
                        }
                        ux0Var.getClass();
                        k7Var2 = new k7(ux0Var, intValue, intValue2, str2);
                        break;
                    case 2:
                        Object obj19 = list7.get(1);
                        c4 c4Var6 = xs0.c;
                        if (o20.e(obj19, Boolean.FALSE) || obj19 == null) {
                            y41Var = null;
                        } else {
                            y41Var = (y41) ((gv) c4Var6.g).e(obj19);
                        }
                        y41Var.getClass();
                        k7Var2 = new k7(y41Var, intValue, intValue2, str2);
                        break;
                    case 3:
                        Object obj20 = list7.get(1);
                        c4 c4Var7 = xs0.d;
                        if (o20.e(obj20, Boolean.FALSE) || obj20 == null) {
                            g41Var = null;
                        } else {
                            g41Var = (g41) ((gv) c4Var7.g).e(obj20);
                        }
                        g41Var.getClass();
                        k7Var2 = new k7(g41Var, intValue, intValue2, str2);
                        break;
                    case 4:
                        Object obj21 = list7.get(1);
                        c4 c4Var8 = xs0.e;
                        if (o20.e(obj21, Boolean.FALSE) || obj21 == null) {
                            d90Var = null;
                        } else {
                            d90Var = (d90) ((gv) c4Var8.g).e(obj21);
                        }
                        d90Var.getClass();
                        k7Var2 = new k7(d90Var, intValue, intValue2, str2);
                        break;
                    case 5:
                        Object obj22 = list7.get(1);
                        c4 c4Var9 = xs0.f;
                        if (o20.e(obj22, Boolean.FALSE) || obj22 == null) {
                            c90Var = null;
                        } else {
                            c90Var = (c90) ((gv) c4Var9.g).e(obj22);
                        }
                        c90Var.getClass();
                        k7Var2 = new k7(c90Var, intValue, intValue2, str2);
                        break;
                    case 6:
                        Object obj23 = list7.get(1);
                        if (obj23 != null) {
                            str3 = (String) obj23;
                        } else {
                            str3 = null;
                        }
                        str3.getClass();
                        k7Var2 = new k7(new ty0(str3), intValue, intValue2, str2);
                        break;
                    default:
                        v7.k();
                        return null;
                }
                return k7Var2;
            case 15:
                obj.getClass();
                return new v80(((Integer) obj).intValue());
            case 16:
                if (obj != null) {
                    str4 = (String) obj;
                } else {
                    str4 = null;
                }
                str4.getClass();
                return new y41(str4);
            case 17:
                if (obj != null) {
                    str5 = (String) obj;
                } else {
                    str5 = null;
                }
                str5.getClass();
                return new g41(str5);
            case 18:
                obj.getClass();
                List list8 = (List) obj;
                Object obj24 = list8.get(0);
                ws0 ws0Var5 = xs0.q;
                Boolean bool5 = Boolean.FALSE;
                o20.e(obj24, bool5);
                if (obj24 != null) {
                    t01Var = (t01) ws0Var5.f.e(obj24);
                } else {
                    t01Var = null;
                }
                t01Var.getClass();
                int i6 = t01Var.a;
                Object obj25 = list8.get(1);
                ws0 ws0Var6 = xs0.r;
                o20.e(obj25, bool5);
                if (obj25 != null) {
                    y01Var = (y01) ws0Var6.f.e(obj25);
                } else {
                    y01Var = null;
                }
                y01Var.getClass();
                int i7 = y01Var.a;
                Object obj26 = list8.get(2);
                u11[] u11VarArr = t11.b;
                ws0 ws0Var7 = xs0.v;
                o20.e(obj26, bool5);
                if (obj26 != null) {
                    t11Var = (t11) ws0Var7.f.e(obj26);
                } else {
                    t11Var = null;
                }
                t11Var.getClass();
                long j = t11Var.a;
                Object obj27 = list8.get(3);
                c11 c11Var2 = c11.c;
                c4 c4Var10 = xs0.l;
                if (o20.e(obj27, bool5) || obj27 == null) {
                    c11Var = null;
                } else {
                    c11Var = (c11) ((gv) c4Var10.g).e(obj27);
                }
                Object obj28 = list8.get(4);
                c4 c4Var11 = k81.g;
                if (o20.e(obj28, bool5) || obj28 == null) {
                    mm0Var = null;
                } else {
                    mm0Var = (mm0) ((gv) c4Var11.g).e(obj28);
                }
                Object obj29 = list8.get(5);
                x80 x80Var2 = x80.d;
                c4 c4Var12 = xs0.A;
                if (o20.e(obj29, bool5) || obj29 == null) {
                    x80Var = null;
                } else {
                    x80Var = (x80) ((gv) c4Var12.g).e(obj29);
                }
                Object obj30 = list8.get(6);
                c4 c4Var13 = k81.i;
                if (o20.e(obj30, bool5) || obj30 == null) {
                    s80Var = null;
                } else {
                    s80Var = (s80) ((gv) c4Var13.g).e(obj30);
                }
                s80Var.getClass();
                int i8 = s80Var.a;
                Object obj31 = list8.get(7);
                ws0 ws0Var8 = xs0.s;
                o20.e(obj31, bool5);
                if (obj31 != null) {
                    yyVar = (yy) ws0Var8.f.e(obj31);
                } else {
                    yyVar = null;
                }
                yyVar.getClass();
                int i9 = yyVar.a;
                Object obj32 = list8.get(8);
                c4 c4Var14 = k81.j;
                if (o20.e(obj32, bool5) || obj32 == null) {
                    i = i8;
                    i2 = i9;
                    l11Var = null;
                } else {
                    l11Var = (l11) ((gv) c4Var14.g).e(obj32);
                    i = i8;
                    i2 = i9;
                }
                return new ck0(i6, i7, j, c11Var, mm0Var, x80Var, i, i2, l11Var);
            case 19:
                obj.getClass();
                List list9 = (List) obj;
                Object obj33 = list9.get(0);
                int i10 = se.h;
                Boolean bool6 = Boolean.FALSE;
                o20.e(obj33, bool6);
                if (obj33 != null) {
                    if (obj33.equals(bool6)) {
                        seVar = new se(se.g);
                    } else {
                        seVar = new se(f31.e(((Integer) obj33).intValue()));
                    }
                } else {
                    seVar = null;
                }
                seVar.getClass();
                long j2 = seVar.a;
                Object obj34 = list9.get(1);
                u11[] u11VarArr2 = t11.b;
                gv gvVar = xs0.v.f;
                o20.e(obj34, bool6);
                if (obj34 != null) {
                    t11Var2 = (t11) gvVar.e(obj34);
                } else {
                    t11Var2 = null;
                }
                t11Var2.getClass();
                long j3 = t11Var2.a;
                Object obj35 = list9.get(2);
                nu nuVar2 = nu.f;
                c4 c4Var15 = xs0.m;
                if (o20.e(obj35, bool6) || obj35 == null) {
                    nuVar = null;
                } else {
                    nuVar = (nu) ((gv) c4Var15.g).e(obj35);
                }
                Object obj36 = list9.get(3);
                c4 c4Var16 = xs0.t;
                if (o20.e(obj36, bool6) || obj36 == null) {
                    luVar = null;
                } else {
                    luVar = (lu) ((gv) c4Var16.g).e(obj36);
                }
                Object obj37 = list9.get(4);
                c4 c4Var17 = xs0.u;
                if (o20.e(obj37, bool6) || obj37 == null) {
                    muVar = null;
                } else {
                    muVar = (mu) ((gv) c4Var17.g).e(obj37);
                }
                Object obj38 = list9.get(6);
                if (obj38 != null) {
                    str6 = (String) obj38;
                } else {
                    str6 = null;
                }
                Object obj39 = list9.get(7);
                o20.e(obj39, bool6);
                if (obj39 != null) {
                    t11Var3 = (t11) gvVar.e(obj39);
                } else {
                    t11Var3 = null;
                }
                t11Var3.getClass();
                long j4 = t11Var3.a;
                Object obj40 = list9.get(8);
                c4 c4Var18 = xs0.n;
                if (o20.e(obj40, bool6) || obj40 == null) {
                    t9Var = null;
                } else {
                    t9Var = (t9) ((gv) c4Var18.g).e(obj40);
                }
                Object obj41 = list9.get(9);
                c4 c4Var19 = xs0.k;
                if (o20.e(obj41, bool6) || obj41 == null) {
                    b11Var = null;
                } else {
                    b11Var = (b11) ((gv) c4Var19.g).e(obj41);
                }
                Object obj42 = list9.get(10);
                ua0 ua0Var2 = ua0.g;
                c4 c4Var20 = xs0.y;
                if (o20.e(obj42, bool6) || obj42 == null) {
                    ua0Var = null;
                } else {
                    ua0Var = (ua0) ((gv) c4Var20.g).e(obj42);
                }
                Object obj43 = list9.get(11);
                o20.e(obj43, bool6);
                if (obj43 != null) {
                    if (obj43.equals(bool6)) {
                        seVar2 = new se(se.g);
                    } else {
                        seVar2 = new se(f31.e(((Integer) obj43).intValue()));
                    }
                } else {
                    seVar2 = null;
                }
                seVar2.getClass();
                long j5 = seVar2.a;
                Object obj44 = list9.get(12);
                c4 c4Var21 = xs0.j;
                if (o20.e(obj44, bool6) || obj44 == null) {
                    w01Var = null;
                } else {
                    w01Var = (w01) ((gv) c4Var21.g).e(obj44);
                }
                Object obj45 = list9.get(13);
                tv0 tv0Var2 = tv0.d;
                c4 c4Var22 = xs0.o;
                if (o20.e(obj45, bool6) || obj45 == null) {
                    tv0Var = null;
                } else {
                    tv0Var = (tv0) ((gv) c4Var22.g).e(obj45);
                }
                return new ux0(j2, j3, nuVar, luVar, muVar, (sl) null, str6, j4, t9Var, b11Var, ua0Var, j5, w01Var, tv0Var, 49184);
            case 20:
                obj.getClass();
                List list10 = (List) obj;
                Object obj46 = list10.get(0);
                if (obj46 != null) {
                    bool = (Boolean) obj46;
                } else {
                    bool = null;
                }
                bool.getClass();
                boolean booleanValue = bool.booleanValue();
                Object obj47 = list10.get(1);
                c4 c4Var23 = k81.h;
                if (o20.e(obj47, Boolean.FALSE) || obj47 == null) {
                    yqVar = null;
                } else {
                    yqVar = (yq) ((gv) c4Var23.g).e(obj47);
                }
                yqVar.getClass();
                return new mm0(yqVar.a, booleanValue);
            case 21:
                obj.getClass();
                return new yq(((Integer) obj).intValue());
            case 22:
                obj.getClass();
                return new s80(((Integer) obj).intValue());
            case 23:
                obj.getClass();
                List list11 = (List) obj;
                Object obj48 = list11.get(0);
                c4 c4Var24 = k81.k;
                if (o20.e(obj48, Boolean.FALSE) || obj48 == null) {
                    k11Var = null;
                } else {
                    k11Var = (k11) ((gv) c4Var24.g).e(obj48);
                }
                k11Var.getClass();
                int i11 = k11Var.a;
                Object obj49 = list11.get(1);
                if (obj49 != null) {
                    bool2 = (Boolean) obj49;
                } else {
                    bool2 = null;
                }
                bool2.getClass();
                return new l11(i11, bool2.booleanValue());
            case 24:
                obj.getClass();
                return new k11(((Integer) obj).intValue());
            case 25:
                return new nt0(((Integer) obj).intValue());
            case 26:
                an0 an0Var = (an0) obj;
                if (an0Var != null && an0Var.a == 2) {
                    i4 = 1;
                }
                return Boolean.valueOf(i4 ^ 1);
            case 27:
                ts0 ts0Var = cx0.a;
                return x31Var;
            case 28:
                return x31Var;
            default:
                return Boolean.TRUE;
        }
    }
}
