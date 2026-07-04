package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ya {
    public static final ve0 a;
    public static final xa b;

    static {
        c(true);
        a = c(false);
        b = xa.b;
    }

    public static final void a(cd0 cd0Var, bw bwVar, int i) {
        int i2;
        boolean z;
        bwVar.W(-211209833);
        if (bwVar.f(cd0Var)) {
            i2 = 4;
        } else {
            i2 = 2;
        }
        int i3 = i2 | i;
        int i4 = 0;
        if ((i3 & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            long j = bwVar.T;
            int i5 = (int) (j ^ (j >>> 32));
            cd0 B = dl.B(bwVar, cd0Var);
            ll0 l = bwVar.l();
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, b);
            m20.F(ih.d, bwVar, l);
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            m20.F(ih.f, bwVar, Integer.valueOf(i5));
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new wa(i, i4, cd0Var);
        }
    }

    public static final void b(dm0 dm0Var, em0 em0Var, kc0 kc0Var, m40 m40Var, int i, int i2, ba baVar) {
        va vaVar;
        ba baVar2;
        ba baVar3;
        Object A = kc0Var.A();
        if (A instanceof va) {
            vaVar = (va) A;
        } else {
            vaVar = null;
        }
        if (vaVar != null && (baVar3 = vaVar.s) != null) {
            baVar2 = baVar3;
        } else {
            baVar2 = baVar;
        }
        dm0.A(dm0Var, em0Var, baVar2.a((em0Var.e << 32) | (em0Var.f & 4294967295L), (i << 32) | (i2 & 4294967295L), m40Var));
    }

    public static final ve0 c(boolean z) {
        ve0 ve0Var = new ve0(9);
        ba baVar = x1.g;
        ve0Var.m(baVar, new bb(baVar, z));
        ba baVar2 = x1.h;
        ve0Var.m(baVar2, new bb(baVar2, z));
        ba baVar3 = x1.i;
        ve0Var.m(baVar3, new bb(baVar3, z));
        ba baVar4 = x1.j;
        ve0Var.m(baVar4, new bb(baVar4, z));
        ba baVar5 = x1.k;
        ve0Var.m(baVar5, new bb(baVar5, z));
        ba baVar6 = x1.l;
        ve0Var.m(baVar6, new bb(baVar6, z));
        ba baVar7 = x1.m;
        ve0Var.m(baVar7, new bb(baVar7, z));
        ba baVar8 = x1.n;
        ve0Var.m(baVar8, new bb(baVar8, z));
        ba baVar9 = x1.o;
        ve0Var.m(baVar9, new bb(baVar9, z));
        return ve0Var;
    }

    public static final pc0 d(ba baVar) {
        pc0 pc0Var = (pc0) a.g(baVar);
        if (pc0Var == null) {
            return new bb(baVar, false);
        }
        return pc0Var;
    }
}
