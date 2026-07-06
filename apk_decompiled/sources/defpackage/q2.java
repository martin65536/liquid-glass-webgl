package defpackage;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.CancellationSignal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q2 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public q2(j2 j2Var, ho hoVar, ap0 ap0Var) {
        super(1);
        this.f = 11;
        this.g = ap0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        v21 v21Var = v21.f;
        v21 v21Var2 = v21.e;
        float f = 0.0f;
        int i2 = 5;
        int i3 = 0;
        Object obj2 = null;
        int i4 = 1;
        x31 x31Var = x31.a;
        Object obj3 = this.g;
        switch (i) {
            case 0:
                up upVar = (up) obj;
                upVar.getClass();
                ((gv) obj3).e(upVar);
                return x31Var;
            case 1:
                a3 a3Var = (a3) obj;
                a50 a50Var = (a50) obj3;
                if (a3Var.Y() != Integer.MAX_VALUE) {
                    if (a3Var.r().b) {
                        a3Var.N();
                    }
                    for (Map.Entry entry : a3Var.r().g.entrySet()) {
                        a50.a(a50Var, (ry) entry.getKey(), ((Number) entry.getValue()).intValue(), a3Var.I());
                    }
                    ng0 ng0Var = a3Var.I().u;
                    ng0Var.getClass();
                    while (!ng0Var.equals(a50Var.a.I())) {
                        for (ry ryVar : a50Var.b(ng0Var).keySet()) {
                            a50.a(a50Var, ryVar, a50Var.c(ng0Var, ryVar), ng0Var);
                        }
                        ng0Var = ng0Var.u;
                        ng0Var.getClass();
                    }
                }
                return x31Var;
            case 2:
                return Boolean.valueOf(((pt) obj).K0(((bt) obj3).a));
            case 3:
                lb0 lb0Var = (lb0) obj;
                b4 b4Var = ((q3) obj3).t;
                if (b4Var.getInsetsListener().k.g() > 0) {
                    he0 he0Var = p71.a;
                    lb0Var.e = true;
                    ob0 ob0Var = lb0Var.h;
                    l40 t0 = ob0Var.t0();
                    if (v10.a(lb0Var.f, 9223372034707292159L)) {
                        lb0Var.f = f31.L(t0.u(0L));
                        lb0Var.g = t0.X();
                    }
                    ob0Var.v0().I.b();
                    long X = t0.X();
                    ve0 ve0Var = b4Var.getInsetsListener().j;
                    int i5 = (int) (X >> 32);
                    int i6 = (int) (X & 4294967295L);
                    for (n71 n71Var : p71.b) {
                        Object g = ve0Var.g(n71Var);
                        g.getClass();
                        c81 c81Var = (c81) g;
                        p71.a(lb0Var, ((o71) n71Var).c, c81Var.h, i5, i6);
                        if (((Boolean) c81Var.b.getValue()).booleanValue()) {
                            p71.a(lb0Var, c81Var.f, c81Var.j, i5, i6);
                            p71.a(lb0Var, c81Var.g, c81Var.k, i5, i6);
                        }
                        p71.a(lb0Var, ((o71) n71Var).d, c81Var.i, i5, i6);
                    }
                    pe0 pe0Var = b4Var.getInsetsListener().l;
                    if (pe0Var.i()) {
                        mx0 mx0Var = b4Var.getInsetsListener().m;
                        Object[] objArr = pe0Var.a;
                        int i7 = pe0Var.b;
                        while (i3 < i7) {
                            af0 af0Var = (af0) objArr[i3];
                            x00 x00Var = (x00) mx0Var.get(i3);
                            Rect rect = (Rect) af0Var.getValue();
                            lb0Var.r(x00Var.b(), rect.left);
                            lb0Var.r(x00Var.d(), rect.top);
                            lb0Var.r(x00Var.c(), rect.right);
                            lb0Var.r(x00Var.a(), rect.bottom);
                            i3++;
                        }
                    }
                }
                return x31Var;
            case 4:
                return Boolean.valueOf(((t10) obj3).a(((su0) obj).f));
            case 5:
                return Boolean.valueOf(n20.g((su0) obj, (Resources) obj3));
            case 6:
                h7 h7Var = (h7) obj;
                float f2 = h7Var.b;
                if (f2 < 0.0f) {
                    f2 = 0.0f;
                }
                float f3 = 1.0f;
                if (f2 > 1.0f) {
                    f2 = 1.0f;
                }
                float f4 = h7Var.c;
                float f5 = -0.5f;
                if (f4 < -0.5f) {
                    f4 = -0.5f;
                }
                float f6 = 0.5f;
                if (f4 > 0.5f) {
                    f4 = 0.5f;
                }
                float f7 = h7Var.d;
                if (f7 >= -0.5f) {
                    f5 = f7;
                }
                if (f5 <= 0.5f) {
                    f6 = f5;
                }
                float f8 = h7Var.a;
                if (f8 >= 0.0f) {
                    f = f8;
                }
                if (f <= 1.0f) {
                    f3 = f;
                }
                return new se(se.a(f31.d(f2, f4, f6, f3, af.x), (we) obj3));
            case 7:
                a70 a70Var = (a70) obj;
                a70Var.getClass();
                d3.r(a70Var, ng.a);
                a70Var.a.a(100, new r7(obj2, j70.f, new gg(1342151075, true, new mg(i3, (c40) obj3)), i2));
                d3.r(a70Var, ng.b);
                return x31Var;
            case 8:
                np npVar = (np) obj;
                npVar.getClass();
                ue.a(npVar, -0.1f, 0.75f);
                o4.o(npVar, npVar.e * 2.0f);
                ju0 ju0Var = (ju0) obj3;
                float f9 = npVar.e * 48.0f;
                ju0Var.getClass();
                if (y20.n()) {
                    g30.F(npVar, "SdfShader", "\nuniform shader content;\nuniform shader sdfTex;\n\nuniform float2 size;\nuniform float2 sdfTexSize;\nuniform float refractionHeight;\nuniform float lightAngle;\n\nfloat circleMap(float x) {\n    return 1.0 - sqrt(1.0 - x * x);\n}\n\nhalf4 main(float2 coord) {\n    half2 p = coord / size * sdfTexSize;\n    if (p.x < 0.0 || p.y < 0.0 || p.x >= sdfTexSize.x || p.y >= sdfTexSize.y) {\n        return half4(0.0);\n    }\n    half4 v = sdfTex.eval(p);\n    float sd = v.r * 2.0 - 1.0;\n    v.a = smoothstep(0.5, 1.0, v.a);\n    if (v.a <= 0.0) {\n        return half4(0.0);\n    }\n    if (v.a < 1.0) {\n        sd = 0.0;\n    }\n    float2 normal = normalize(v.gb * 2.0 - 1.0);\n    \n    float intensity = circleMap(1.0 - min(1.0, -sd * 1.5));\n    float2 refractedCoord = coord - intensity * refractionHeight * normal;\n\n    half4 color = content.eval(refractedCoord) * v.a;\n    float2 lightDir = float2(cos(lightAngle * 3.1415926 / 180.0), sin(lightAngle * 3.1415926 / 180.0));\n    float bevelIntensity = clamp(dot(normal, lightDir), 0.0, 1.0);\n    color.rgb *= 1.0 + 0.5 * intensity * bevelIntensity;\n    bevelIntensity = clamp(dot(normal, -lightDir), 0.0, 1.0);\n    color.rgb *= 1.0 + 0.5 * bevelIntensity * min(1.0, smoothstep(1.0, 0.0, abs(intensity - 0.25) * 6.0));\n    return color;\n}", new iu0(ju0Var, npVar, f9));
                }
                return x31Var;
            case 9:
                if (((Throwable) obj) != null) {
                    ((CancellationSignal) obj3).cancel();
                }
                return x31Var;
            case 10:
                return new h2(i4, (vn) obj3);
            case 11:
                ho hoVar = (ho) obj;
                if (hoVar.r) {
                    if (hoVar.t != null) {
                        q00.b("DragAndDropTarget self reference must be null at the start of a drag and drop session");
                    }
                    hoVar.t = null;
                    ap0 ap0Var = (ap0) obj3;
                    ap0Var.e = ap0Var.e;
                    return v21Var2;
                }
                return v21Var;
            case 12:
                ho hoVar2 = (ho) obj;
                if (hoVar2.e.r) {
                    ho hoVar3 = hoVar2.t;
                    if (hoVar3 != null) {
                        q2 q2Var = new q2(12, (j2) obj3);
                        if (q2Var.e(hoVar3) == v21Var2) {
                            d20.M(hoVar3, q2Var);
                        }
                    }
                    hoVar2.t = null;
                    hoVar2.s = null;
                    return v21Var2;
                }
                return v21Var;
            case 13:
                if (bx.b.compareAndSet(false, true)) {
                    ((zb) obj3).q(x31Var);
                }
                return x31Var;
            case 14:
                up upVar2 = (up) obj;
                hx hxVar = (hx) obj3;
                y5 y5Var = hxVar.l;
                if (hxVar.n && hxVar.w && y5Var != null) {
                    r7 J = upVar2.J();
                    long v = J.v();
                    J.q().h();
                    try {
                        ((r7) ((j2) J.f).f).q().q(y5Var);
                        hxVar.d(upVar2);
                    } finally {
                        J.q().f();
                        J.G(v);
                    }
                } else {
                    hxVar.d(upVar2);
                }
                return x31Var;
            case 15:
                up upVar3 = (up) obj;
                uc q = upVar3.J().q();
                kv kvVar = ((kx) obj3).h;
                if (kvVar != null) {
                    kvVar.d(q, (hx) upVar3.J().g);
                }
                return x31Var;
            case 16:
                h41 h41Var = (h41) obj;
                sx sxVar = (sx) obj3;
                sxVar.g(h41Var);
                gv gvVar = sxVar.i;
                if (gvVar != null) {
                    gvVar.e(h41Var);
                }
                return x31Var;
            case 17:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                float floatValue = ((Number) ((vu) obj3).a()).floatValue();
                lxVar.i(floatValue);
                lxVar.q(floatValue);
                return x31Var;
            case 18:
                ((ef0) obj3).b((ad0) obj);
                return Boolean.TRUE;
            case 19:
                dm0 dm0Var = (dm0) obj;
                ArrayList arrayList = (ArrayList) obj3;
                int size = arrayList.size();
                for (int i8 = 0; i8 < size; i8++) {
                    dm0.E(dm0Var, (em0) arrayList.get(i8), 0, 0);
                }
                return x31Var;
            case 20:
                zu0.a((bv0) obj, ((cr0) obj3).a);
                return x31Var;
            case 21:
                t30[] t30VarArr = zu0.a;
                ((bv0) obj).a(wu0.a, jc0.v((String) obj3));
                return x31Var;
            case 22:
                ((List) obj).add((Float) ((v60) obj3).a());
                return true;
            case 23:
                lx lxVar2 = (lx) obj;
                kw0 kw0Var = (kw0) obj3;
                lxVar2.i(kw0Var.s);
                lxVar2.q(kw0Var.t);
                lxVar2.b(kw0Var.u);
                lxVar2.n(0.0f);
                lxVar2.g(0.0f);
                lxVar2.l();
                lxVar2.a();
                lxVar2.f();
                lxVar2.e(0.0f);
                lxVar2.s(kw0Var.v);
                lxVar2.T(kw0Var.w);
                lxVar2.F(kw0Var.x);
                lxVar2.m(kw0Var.y);
                lxVar2.p(null);
                lxVar2.h(kw0Var.z);
                lxVar2.o(kw0Var.A);
                lxVar2.b0(0);
                lxVar2.k(kw0Var.B);
                lxVar2.d(kw0Var.C);
                return x31Var;
            case 24:
                Throwable th = (Throwable) obj;
                xz0 xz0Var = (xz0) obj3;
                pc pcVar = xz0Var.g;
                if (pcVar != null) {
                    pcVar.x(th);
                }
                xz0Var.g = null;
                return x31Var;
            default:
                ((tn) obj).getClass();
                s31 s31Var = (s31) obj3;
                s31Var.c.registerListener(s31Var.e, s31Var.d, 2);
                return new h2(i2, s31Var);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ q2(int i, Object obj) {
        super(1);
        this.f = i;
        this.g = obj;
    }
}
