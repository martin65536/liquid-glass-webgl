package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class uv extends jc implements tv, p30, sv {
    public final int k;

    public uv(int i, Object obj, Class cls, String str, String str2, int i2, int i3) {
        super(obj, cls, str, str2, (i2 & 1) == 1);
        this.k = i;
    }

    @Override // defpackage.tv
    public final int b() {
        return this.k;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [p30] */
    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof uv) {
                uv uvVar = (uv) obj;
                if (this.h.equals(uvVar.h) && this.i.equals(uvVar.i) && o20.e(this.f, uvVar.f) && g().equals(uvVar.g())) {
                    return true;
                }
                return false;
            }
            if (obj instanceof uv) {
                ?? r0 = this.e;
                if (r0 == 0) {
                    f();
                    this.e = this;
                } else {
                    this = r0;
                }
                return obj.equals(this);
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.jc
    public final p30 f() {
        fp0.a.getClass();
        return this;
    }

    public final int hashCode() {
        g();
        return this.i.hashCode() + ((this.h.hashCode() + (g().hashCode() * 31)) * 31);
    }

    public final String toString() {
        p30 p30Var = this.e;
        if (p30Var == null) {
            f();
            this.e = this;
            p30Var = this;
        }
        if (p30Var != this) {
            return p30Var.toString();
        }
        String str = this.h;
        if ("<init>".equals(str)) {
            return "constructor (Kotlin reflection is not available)";
        }
        return "function " + str + " (Kotlin reflection is not available)";
    }

    public uv(int i, Class cls, String str, String str2, int i2) {
        this(i, ic.e, cls, str, str2, i2, 0);
    }
}
