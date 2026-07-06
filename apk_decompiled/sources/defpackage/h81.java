package defpackage;

import android.view.ViewGroup;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class h81 {
    public static final ViewGroup.LayoutParams a = new ViewGroup.LayoutParams(-2, -2);

    /* JADX WARN: Removed duplicated region for block: B:21:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final defpackage.f81 a(defpackage.p r7, defpackage.nh r8, defpackage.gg r9) {
        /*
            java.util.concurrent.atomic.AtomicBoolean r0 = defpackage.bx.a
            r1 = 0
            r2 = 1
            boolean r0 = r0.compareAndSet(r1, r2)
            r3 = 0
            if (r0 == 0) goto L3f
            r0 = 6
            zb r0 = defpackage.f31.c(r2, r0, r3)
            a01 r4 = defpackage.n6.q
            java.lang.Object r4 = r4.getValue()
            yj r4 = (defpackage.yj) r4
            hj r4 = defpackage.dl.d(r4)
            f r5 = new f
            r5.<init>(r0, r3)
            r6 = 3
            defpackage.f31.G(r4, r3, r5, r6)
            q2 r4 = new q2
            r5 = 13
            r4.<init>(r5, r0)
            java.lang.Object r0 = defpackage.cx0.c
            monitor-enter(r0)
            java.util.List r5 = defpackage.cx0.i     // Catch: java.lang.Throwable -> L3c
            java.util.ArrayList r4 = defpackage.me.a0(r5, r4)     // Catch: java.lang.Throwable -> L3c
            defpackage.cx0.i = r4     // Catch: java.lang.Throwable -> L3c
            monitor-exit(r0)
            defpackage.cx0.a()
            goto L3f
        L3c:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        L3f:
            int r0 = r7.getChildCount()
            if (r0 <= 0) goto L59
            android.view.View r0 = r7.getChildAt(r1)
            boolean r1 = r0 instanceof defpackage.b4
            if (r1 == 0) goto L50
            b4 r0 = (defpackage.b4) r0
            goto L51
        L50:
            r0 = r3
        L51:
            if (r0 == 0) goto L57
            r0.setComposeViewContext(r8)
            goto L5d
        L57:
            r0 = r3
            goto L5d
        L59:
            r7.removeAllViews()
            goto L57
        L5d:
            if (r0 != 0) goto L71
            b4 r0 = new b4
            android.content.Context r1 = r7.getContext()
            r0.<init>(r1, r8)
            android.view.View r1 = r0.getView()
            android.view.ViewGroup$LayoutParams r4 = defpackage.h81.a
            r7.addView(r1, r4)
        L71:
            r0.setComposeViewContext(r8)
            nh r7 = r7.getComposeViewContext$ui()
            if (r7 == 0) goto L80
            r8.c()
            r0.setComposeViewContextIncrementedDuringInit$ui(r2)
        L80:
            r7 = 2131034234(0x7f05007a, float:1.767898E38)
            java.lang.Object r1 = r0.getTag(r7)
            boolean r2 = r1 instanceof defpackage.f81
            if (r2 == 0) goto L8e
            r3 = r1
            f81 r3 = (defpackage.f81) r3
        L8e:
            if (r3 != 0) goto La8
            f81 r3 = new f81
            r7 r1 = new r7
            z40 r2 = r0.getRoot()
            r1.<init>(r2)
            th r2 = r8.b
            yh r4 = new yh
            r4.<init>(r2, r1)
            r3.<init>(r0, r4)
            r0.setTag(r7, r3)
        La8:
            r3.i(r9)
            th r7 = r8.b
            g81 r8 = new g81
            r8.<init>(r7)
            r0.setFrameEndScheduler$ui(r8)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h81.a(p, nh, gg):f81");
    }
}
