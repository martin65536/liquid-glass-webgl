package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z80 implements iu {
    public final float a;

    public z80(float f) {
        this.a = f;
    }

    @Override // defpackage.iu
    public final float a(float f) {
        return f / this.a;
    }

    @Override // defpackage.iu
    public final float b(float f) {
        return f * this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof z80) && Float.compare(this.a, ((z80) obj).a) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.a);
    }

    public final String toString() {
        return d3.v(new StringBuilder("LinearFontScaleConverter(fontScale="), this.a, ')');
    }
}
