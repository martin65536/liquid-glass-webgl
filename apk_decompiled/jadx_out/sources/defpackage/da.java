package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class da extends te {
    public final long b;
    public final int c;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public da(int r4, long r5) {
        /*
            r3 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 < r1) goto L16
            defpackage.h3.e()
            int r0 = defpackage.f31.P(r5)
            android.graphics.BlendMode r1 = defpackage.f31.O(r4)
            android.graphics.BlendModeColorFilter r0 = defpackage.h3.c(r0, r1)
            goto L23
        L16:
            android.graphics.PorterDuffColorFilter r0 = new android.graphics.PorterDuffColorFilter
            int r1 = defpackage.f31.P(r5)
            android.graphics.PorterDuff$Mode r2 = defpackage.f31.S(r4)
            r0.<init>(r1, r2)
        L23:
            r3.<init>(r0)
            r3.b = r5
            r3.c = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.da.<init>(int, long):void");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof da)) {
            return false;
        }
        da daVar = (da) obj;
        if (se.c(this.b, daVar.b) && this.c == daVar.c) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (se.i(this.b) * 31) + this.c;
    }

    public final String toString() {
        return "BlendModeColorFilter(color=" + ((Object) se.j(this.b)) + ", blendMode=" + ((Object) f31.T(this.c)) + ')';
    }
}
