package defpackage;

import java.io.Serializable;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mr extends w implements lr, RandomAccess, Serializable {
    public final Enum[] e;

    public mr(Enum[] enumArr) {
        this.e = enumArr;
    }

    @Override // defpackage.m
    public final int a() {
        return this.e.length;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018 A[RETURN] */
    @Override // defpackage.m, java.util.Collection, java.util.List
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean contains(java.lang.Object r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof java.lang.Enum
            if (r0 != 0) goto L5
            goto L1a
        L5:
            java.lang.Enum r3 = (java.lang.Enum) r3
            int r0 = r3.ordinal()
            if (r0 < 0) goto L15
            java.lang.Enum[] r2 = r2.e
            int r1 = r2.length
            if (r0 >= r1) goto L15
            r2 = r2[r0]
            goto L16
        L15:
            r2 = 0
        L16:
            if (r2 != r3) goto L1a
            r2 = 1
            return r2
        L1a:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mr.contains(java.lang.Object):boolean");
    }

    @Override // java.util.List
    public final Object get(int i) {
        Enum[] enumArr = this.e;
        int length = enumArr.length;
        if (i >= 0 && i < length) {
            return enumArr[i];
        }
        v7.f(d3.u("index: ", i, ", size: ", length));
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0019 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001a A[RETURN] */
    @Override // defpackage.w, java.util.List
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int indexOf(java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof java.lang.Enum
            r1 = -1
            if (r0 != 0) goto L6
            return r1
        L6:
            java.lang.Enum r4 = (java.lang.Enum) r4
            int r0 = r4.ordinal()
            if (r0 < 0) goto L16
            java.lang.Enum[] r3 = r3.e
            int r2 = r3.length
            if (r0 >= r2) goto L16
            r3 = r3[r0]
            goto L17
        L16:
            r3 = 0
        L17:
            if (r3 != r4) goto L1a
            return r0
        L1a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mr.indexOf(java.lang.Object):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0019 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001a A[RETURN] */
    @Override // defpackage.w, java.util.List
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int lastIndexOf(java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof java.lang.Enum
            r1 = -1
            if (r0 != 0) goto L6
            return r1
        L6:
            java.lang.Enum r4 = (java.lang.Enum) r4
            int r0 = r4.ordinal()
            if (r0 < 0) goto L16
            java.lang.Enum[] r3 = r3.e
            int r2 = r3.length
            if (r0 >= r2) goto L16
            r3 = r3[r0]
            goto L17
        L16:
            r3 = 0
        L17:
            if (r3 != r4) goto L1a
            return r0
        L1a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mr.lastIndexOf(java.lang.Object):int");
    }
}
