package defpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kf extends z30 implements kv {
    public static final kf A;
    public static final kf B;
    public static final kf C;
    public static final kf D;
    public static final kf E;
    public static final kf F;
    public static final kf G;
    public static final kf H;
    public static final kf I;
    public static final kf J;
    public static final kf g;
    public static final kf h;
    public static final kf i;
    public static final kf j;
    public static final kf k;
    public static final kf l;
    public static final kf m;
    public static final kf n;
    public static final kf o;
    public static final kf p;
    public static final kf q;
    public static final kf r;
    public static final kf s;
    public static final kf t;
    public static final kf u;
    public static final kf v;
    public static final kf w;
    public static final kf x;
    public static final kf y;
    public static final kf z;
    public final /* synthetic */ int f;

    static {
        int i2 = 2;
        g = new kf(i2, 0);
        h = new kf(i2, 1);
        i = new kf(i2, 2);
        j = new kf(i2, 3);
        k = new kf(i2, 4);
        l = new kf(i2, 5);
        m = new kf(i2, 6);
        n = new kf(i2, 7);
        o = new kf(i2, 8);
        p = new kf(i2, 9);
        q = new kf(i2, 10);
        r = new kf(i2, 11);
        s = new kf(i2, 12);
        t = new kf(i2, 13);
        u = new kf(i2, 14);
        v = new kf(i2, 15);
        w = new kf(i2, 16);
        x = new kf(i2, 17);
        y = new kf(i2, 18);
        z = new kf(i2, 19);
        A = new kf(i2, 20);
        B = new kf(i2, 21);
        C = new kf(i2, 22);
        D = new kf(i2, 23);
        E = new kf(i2, 24);
        F = new kf(i2, 25);
        G = new kf(i2, 26);
        H = new kf(i2, 27);
        I = new kf(i2, 28);
        J = new kf(i2, 29);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ kf(int i2, int i3) {
        super(i2);
        this.f = i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v20 */
    /* JADX WARN: Type inference failed for: r14v21, types: [bd0] */
    /* JADX WARN: Type inference failed for: r14v25 */
    /* JADX WARN: Type inference failed for: r14v26, types: [bd0] */
    /* JADX WARN: Type inference failed for: r14v27, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v29 */
    /* JADX WARN: Type inference failed for: r14v30 */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v58 */
    /* JADX WARN: Type inference failed for: r14v59 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [ef0] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [ef0] */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z2;
        int i2 = this.f;
        int i3 = 2;
        boolean z3 = true;
        boolean z4 = false;
        boolean z5 = false;
        x31 x31Var = x31.a;
        switch (i2) {
            case 0:
                String str = (String) obj;
                ad0 ad0Var = (ad0) obj2;
                if (str.length() == 0) {
                    return ad0Var.toString();
                }
                return str + ", " + ad0Var;
            case 1:
                up upVar = (up) obj;
                gv gvVar = (gv) obj2;
                upVar.getClass();
                gvVar.getClass();
                gvVar.e(upVar);
                d3.q(upVar, se.b(se.c, 0.25f), 0L, 0.0f, 0, 126);
                return x31Var;
            case 2:
                bw bwVar = (bw) obj;
                int intValue = ((Number) obj2).intValue();
                if ((intValue & 3) != 2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bwVar.O(intValue & 1, z2)) {
                    Object[] objArr = new Object[0];
                    Object L = bwVar.L();
                    dt0 dt0Var = ph.a;
                    Object obj3 = L;
                    if (L == dt0Var) {
                        n2 n2Var = n2.w;
                        bwVar.f0(n2Var);
                        obj3 = n2Var;
                    }
                    af0 af0Var = (af0) y20.r(objArr, (vu) obj3, bwVar);
                    switch (((bd) af0Var.getValue()).ordinal()) {
                        case 0:
                            bwVar.V(1057968835);
                            boolean f = bwVar.f(af0Var);
                            Object L2 = bwVar.L();
                            Object obj4 = L2;
                            if (f || L2 == dt0Var) {
                                og ogVar = new og(af0Var, i3);
                                bwVar.f0(ogVar);
                                obj4 = ogVar;
                            }
                            o4.d((gv) obj4, bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 1:
                            bwVar.V(1057971685);
                            o4.b(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 2:
                            bwVar.V(1057973540);
                            g30.h(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 3:
                            bwVar.V(1057975364);
                            d20.a(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 4:
                            bwVar.V(1057977320);
                            dl.c(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 5:
                            bwVar.V(1057979268);
                            n20.c(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 6:
                            bwVar.V(1057981256);
                            t20.b(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 7:
                            bwVar.V(1057983435);
                            n20.b(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 8:
                            bwVar.V(1057985575);
                            g30.d(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 9:
                            bwVar.V(1057987821);
                            dl.h(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 10:
                            bwVar.V(1057990452);
                            jc0.a(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 11:
                            bwVar.V(1057993069);
                            g30.f(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 12:
                            bwVar.V(1057995469);
                            g30.g(bwVar, 0);
                            bwVar.p(false);
                            break;
                        case 13:
                            bwVar.V(1057998001);
                            n30.a(bwVar, 0);
                            bwVar.p(false);
                            break;
                        default:
                            bwVar.V(1057967879);
                            bwVar.p(false);
                            v7.k();
                            return null;
                    }
                    if (((bd) af0Var.getValue()) == bd.e) {
                        z3 = false;
                    }
                    boolean f2 = bwVar.f(af0Var);
                    Object L3 = bwVar.L();
                    Object obj5 = L3;
                    if (f2 || L3 == dt0Var) {
                        qg qgVar = new qg(af0Var, z4 ? 1 : 0);
                        bwVar.f0(qgVar);
                        obj5 = qgVar;
                    }
                    f31.a(z3, (vu) obj5, bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
            case 3:
                bw bwVar2 = (bw) obj;
                int intValue2 = ((Number) obj2).intValue();
                if ((intValue2 & 3) != 2) {
                    z4 = true;
                }
                if (!bwVar2.O(intValue2 & 1, z4)) {
                    bwVar2.R();
                }
                return x31Var;
            case 4:
                bw bwVar3 = (bw) obj;
                int intValue3 = ((Number) obj2).intValue();
                if ((intValue3 & 3) != 2) {
                    z5 = true;
                }
                if (!bwVar3.O(intValue3 & 1, z5)) {
                    bwVar3.R();
                }
                return x31Var;
            case 5:
                ((Number) obj2).intValue();
                ((z40) ((jh) obj)).getClass();
                return x31Var;
            case 6:
                pc0 pc0Var = (pc0) obj2;
                z40 z40Var = (z40) ((jh) obj);
                if (!o20.e(z40Var.z, pc0Var)) {
                    z40Var.z = pc0Var;
                    z40Var.B();
                }
                return x31Var;
            case 7:
                ((z40) ((jh) obj)).a0((cd0) obj2);
                return x31Var;
            case 8:
                ci ciVar = (ci) obj2;
                z40 z40Var2 = (z40) ((jh) obj);
                z40Var2.D = ciVar;
                lg0 lg0Var = z40Var2.H;
                qy0 qy0Var = fi.h;
                ll0 ll0Var = (ll0) ciVar;
                ll0Var.getClass();
                z40Var2.X((mm) jc0.A(ll0Var, qy0Var));
                ll0 ll0Var2 = (ll0) ciVar;
                m40 m40Var = (m40) jc0.A(ll0Var2, fi.n);
                if (z40Var2.B != m40Var) {
                    z40Var2.B = m40Var;
                    z40Var2.B();
                    z40 s2 = z40Var2.s();
                    if (s2 != null) {
                        s2.z();
                    } else {
                        mj0 mj0Var = z40Var2.r;
                        if (mj0Var != null) {
                            ((b4) mj0Var).invalidate();
                        }
                    }
                    z40Var2.A();
                    for (bd0 bd0Var = lg0Var.f; bd0Var != null; bd0Var = bd0Var.j) {
                        bd0Var.w0();
                    }
                }
                z40Var2.b0((l51) jc0.A(ll0Var2, fi.t));
                bd0 bd0Var2 = lg0Var.f;
                if ((bd0Var2.h & 32768) != 0) {
                    while (bd0Var2 != null) {
                        if ((bd0Var2.g & 32768) != 0) {
                            jm jmVar = bd0Var2;
                            ?? r1 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof ai) {
                                    bd0 bd0Var3 = ((bd0) ((ai) jmVar)).e;
                                    if (bd0Var3.r) {
                                        og0.c(bd0Var3);
                                    } else {
                                        bd0Var3.n = true;
                                    }
                                } else if ((jmVar.g & 32768) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var4 = jmVar.t;
                                    int i4 = 0;
                                    r1 = r1;
                                    jmVar = jmVar;
                                    while (bd0Var4 != null) {
                                        if ((bd0Var4.g & 32768) != 0) {
                                            i4++;
                                            r1 = r1;
                                            if (i4 == 1) {
                                                jmVar = bd0Var4;
                                            } else {
                                                if (r1 == 0) {
                                                    r1 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r1.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r1.b(bd0Var4);
                                            }
                                        }
                                        bd0Var4 = bd0Var4.j;
                                        r1 = r1;
                                        jmVar = jmVar;
                                    }
                                    if (i4 == 1) {
                                    }
                                }
                                jmVar = k81.h(r1);
                            }
                        }
                        if ((bd0Var2.h & 32768) != 0) {
                            bd0Var2 = bd0Var2.j;
                        }
                    }
                }
                return x31Var;
            case 9:
                long j2 = ((mw0) obj).a;
                return new ch0(((ch0) obj2).a);
            case 10:
                long j3 = ((ch0) obj2).a;
                ((al) obj).getClass();
                return x31Var;
            case 11:
                long j4 = ((ch0) obj2).a;
                ((al) obj).getClass();
                return x31Var;
            case 12:
                long j5 = ((ch0) obj2).a;
                ((al) obj).getClass();
                return x31Var;
            case 13:
                up upVar2 = (up) obj;
                gv gvVar2 = (gv) obj2;
                upVar2.getClass();
                gvVar2.getClass();
                r7 J2 = upVar2.J();
                long v2 = J2.v();
                J2.q().h();
                try {
                    j2 j2Var = (j2) J2.f;
                    j2Var.o(1.5f, 1.5f, o30.p(((r7) j2Var.f).v()));
                    j2Var.q(0.0f, -upVar2.G(80.0f));
                    gvVar2.e(upVar2);
                    return x31Var;
                } finally {
                    J2.q().f();
                    J2.G(v2);
                }
            case 14:
                Collection collection = (List) obj;
                List list = (List) obj2;
                if (collection == null) {
                    collection = er.e;
                }
                return me.b0(collection, list);
            case 15:
                return (u4) obj;
            case 16:
                List list2 = (List) obj;
                List list3 = (List) obj2;
                if (list2 != null) {
                    ArrayList arrayList = new ArrayList(list2);
                    arrayList.addAll(list3);
                    return arrayList;
                }
                return list3;
            case 17:
                return (fj) obj;
            case 18:
                return (h5) obj;
            case 19:
                return (x31) obj;
            case 20:
                return (x31) obj;
            case 21:
                return (x31) obj;
            case 22:
                throw new IllegalStateException("merge function called on unmergeable property PaneTitle.");
            case 23:
                cr0 cr0Var = (cr0) obj;
                int i5 = ((cr0) obj2).a;
                return cr0Var;
            case 24:
                return (zv0) obj;
            case 25:
                return (String) obj;
            case 26:
                List list4 = (List) obj;
                List list5 = (List) obj2;
                if (list4 != null) {
                    ArrayList arrayList2 = new ArrayList(list4);
                    arrayList2.addAll(list5);
                    return arrayList2;
                }
                return list5;
            case 27:
                Float f3 = (Float) obj;
                ((Number) obj2).floatValue();
                return f3;
            case 28:
                return (String) obj;
            default:
                Boolean bool = (Boolean) obj;
                ((Boolean) obj2).booleanValue();
                return bool;
        }
    }
}
