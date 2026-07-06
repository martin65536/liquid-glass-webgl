package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class of {
    public final Object a;
    public final kc b;
    public final lv c;
    public final Object d;
    public final Throwable e;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ of(java.lang.Object r3, defpackage.kc r4, defpackage.lv r5, java.lang.Throwable r6, int r7) {
        /*
            r2 = this;
            r0 = r7 & 2
            r1 = 0
            if (r0 == 0) goto L6
            r4 = r1
        L6:
            r0 = r7 & 4
            if (r0 == 0) goto Lb
            r5 = r1
        Lb:
            r7 = r7 & 16
            if (r7 == 0) goto L11
            r7 = r1
            goto L12
        L11:
            r7 = r6
        L12:
            r6 = 0
            r2.<init>(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.of.<init>(java.lang.Object, kc, lv, java.lang.Throwable, int):void");
    }

    public static of a(of ofVar, kc kcVar, Throwable th, int i) {
        Object obj = ofVar.a;
        if ((i & 2) != 0) {
            kcVar = ofVar.b;
        }
        kc kcVar2 = kcVar;
        lv lvVar = ofVar.c;
        Object obj2 = ofVar.d;
        if ((i & 16) != 0) {
            th = ofVar.e;
        }
        return new of(obj, kcVar2, lvVar, obj2, th);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof of)) {
            return false;
        }
        of ofVar = (of) obj;
        if (o20.e(this.a, ofVar.a) && o20.e(this.b, ofVar.b) && o20.e(this.c, ofVar.c) && o20.e(this.d, ofVar.d) && o20.e(this.e, ofVar.e)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int i = 0;
        Object obj = this.a;
        if (obj == null) {
            hashCode = 0;
        } else {
            hashCode = obj.hashCode();
        }
        int i2 = hashCode * 31;
        kc kcVar = this.b;
        if (kcVar == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = kcVar.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        lv lvVar = this.c;
        if (lvVar == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = lvVar.hashCode();
        }
        int i4 = (i3 + hashCode3) * 31;
        Object obj2 = this.d;
        if (obj2 == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = obj2.hashCode();
        }
        int i5 = (i4 + hashCode4) * 31;
        Throwable th = this.e;
        if (th != null) {
            i = th.hashCode();
        }
        return i5 + i;
    }

    public final String toString() {
        return "CompletedContinuation(result=" + this.a + ", cancelHandler=" + this.b + ", onCancellation=" + this.c + ", idempotentResume=" + this.d + ", cancelCause=" + this.e + ')';
    }

    public of(Object obj, kc kcVar, lv lvVar, Object obj2, Throwable th) {
        this.a = obj;
        this.b = kcVar;
        this.c = lvVar;
        this.d = obj2;
        this.e = th;
    }
}
