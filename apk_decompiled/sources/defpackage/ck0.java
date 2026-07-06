package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ck0 implements j7 {
    public final int a;
    public final int b;
    public final long c;
    public final c11 d;
    public final mm0 e;
    public final x80 f;
    public final int g;
    public final int h;
    public final l11 i;

    public ck0(int i, int i2, long j, c11 c11Var, mm0 mm0Var, x80 x80Var, int i3, int i4, l11 l11Var) {
        this.a = i;
        this.b = i2;
        this.c = j;
        this.d = c11Var;
        this.e = mm0Var;
        this.f = x80Var;
        this.g = i3;
        this.h = i4;
        this.i = l11Var;
        if (!t11.a(j, t11.c) && t11.c(j) < 0.0f) {
            r00.b("lineHeight can't be negative (" + t11.c(j) + ')');
        }
    }

    public final ck0 a(ck0 ck0Var) {
        if (ck0Var == null) {
            return this;
        }
        return dk0.a(this, ck0Var.a, ck0Var.b, ck0Var.c, ck0Var.d, ck0Var.e, ck0Var.f, ck0Var.g, ck0Var.h, ck0Var.i);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ck0) {
                ck0 ck0Var = (ck0) obj;
                if (this.a == ck0Var.a && this.b == ck0Var.b && t11.a(this.c, ck0Var.c) && o20.e(this.d, ck0Var.d) && o20.e(this.e, ck0Var.e) && o20.e(this.f, ck0Var.f) && this.g == ck0Var.g && this.h == ck0Var.h && o20.e(this.i, ck0Var.i)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int d = (t11.d(this.c) + (((this.a * 31) + this.b) * 31)) * 31;
        int i4 = 0;
        c11 c11Var = this.d;
        if (c11Var != null) {
            i = c11Var.hashCode();
        } else {
            i = 0;
        }
        int i5 = (d + i) * 31;
        mm0 mm0Var = this.e;
        if (mm0Var != null) {
            i2 = mm0Var.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        x80 x80Var = this.f;
        if (x80Var != null) {
            i3 = x80Var.hashCode();
        } else {
            i3 = 0;
        }
        int i7 = (((((i6 + i3) * 31) + this.g) * 31) + this.h) * 31;
        l11 l11Var = this.i;
        if (l11Var != null) {
            i4 = l11Var.hashCode();
        }
        return i7 + i4;
    }

    public final String toString() {
        return "ParagraphStyle(textAlign=" + ((Object) t01.a(this.a)) + ", textDirection=" + ((Object) y01.a(this.b)) + ", lineHeight=" + ((Object) t11.e(this.c)) + ", textIndent=" + this.d + ", platformStyle=" + this.e + ", lineHeightStyle=" + this.f + ", lineBreak=" + ((Object) s80.a(this.g)) + ", hyphens=" + ((Object) yy.a(this.h)) + ", textMotion=" + this.i + ')';
    }
}
