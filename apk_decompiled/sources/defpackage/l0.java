package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class l0 {
    public m0[] e;
    public int f;
    public int g;
    public lz0 h;

    public final m0 a() {
        m0 m0Var;
        lz0 lz0Var;
        synchronized (this) {
            try {
                m0[] m0VarArr = this.e;
                if (m0VarArr == null) {
                    m0VarArr = e();
                    this.e = m0VarArr;
                } else if (this.f >= m0VarArr.length) {
                    Object[] copyOf = Arrays.copyOf(m0VarArr, m0VarArr.length * 2);
                    this.e = (m0[]) copyOf;
                    m0VarArr = (m0[]) copyOf;
                }
                int i = this.g;
                do {
                    m0Var = m0VarArr[i];
                    if (m0Var == null) {
                        m0Var = d();
                        m0VarArr[i] = m0Var;
                    }
                    i++;
                    if (i >= m0VarArr.length) {
                        i = 0;
                    }
                } while (!m0Var.a(this));
                this.g = i;
                this.f++;
                lz0Var = this.h;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (lz0Var != null) {
            lz0Var.w(1);
        }
        return m0Var;
    }

    public abstract m0 d();

    public abstract m0[] e();

    public final void f(m0 m0Var) {
        lz0 lz0Var;
        int i;
        ij[] b;
        synchronized (this) {
            try {
                int i2 = this.f - 1;
                this.f = i2;
                lz0Var = this.h;
                if (i2 == 0) {
                    this.g = 0;
                }
                m0Var.getClass();
                b = m0Var.b(this);
            } catch (Throwable th) {
                throw th;
            }
        }
        for (ij ijVar : b) {
            if (ijVar != null) {
                ijVar.u(x31.a);
            }
        }
        if (lz0Var != null) {
            lz0Var.w(-1);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [lz0, ew0] */
    public final lz0 h() {
        lz0 lz0Var;
        synchronized (this) {
            lz0 lz0Var2 = this.h;
            lz0Var = lz0Var2;
            if (lz0Var2 == null) {
                int i = this.f;
                ?? ew0Var = new ew0(1, Integer.MAX_VALUE, xb.f);
                ew0Var.q(Integer.valueOf(i));
                this.h = ew0Var;
                lz0Var = ew0Var;
            }
        }
        return lz0Var;
    }
}
