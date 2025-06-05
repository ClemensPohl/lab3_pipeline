package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.pipeline.pull.PullRenderer;
import at.fhv.sysarch.lab3.pipeline.pull.PullSourceFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage1_model.PullTransformFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.PullViewTransformFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullBackfaceCullingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullColorFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullDepthSortingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullLightingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage3_clip.PullProjectionFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage4_ndc.PullPerspectiveDivisionFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage5_screen.PullViewportTransformFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;

import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // Get model
        PullSourceFilter modelSource = new PullSourceFilter();

        // Stage 1: Model Transform

        Mat4 scaleMatrix = new Mat4(1);
        Mat4 rotationMatrix = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0);
        Mat4 translationMatrix = pd.getModelTranslation();

        PullTransformFilter transformFilter = new PullTransformFilter(modelSource, scaleMatrix, rotationMatrix, translationMatrix);

        // Stage 2: View Transform and Advanced
        PullViewTransformFilter viewTransformFilter = new PullViewTransformFilter(pd.getViewTransform());
        viewTransformFilter.setPredecessor(transformFilter);

        PullBackfaceCullingFilter backfaceCullingFilter = new PullBackfaceCullingFilter();
        backfaceCullingFilter.setPredecessor(viewTransformFilter);

        PullDepthSortingFilter depthSortingFilter = new PullDepthSortingFilter();
        depthSortingFilter.setPredecessor(backfaceCullingFilter);

        PullColorFilter colorFilter = new PullColorFilter(pd.getModelColor());
        colorFilter.setPredecessor(depthSortingFilter);

        PullFilter<Pair<Face, Color>> pipelineOutput;

        if (pd.isPerformLighting()) {
            PullLightingFilter lightingFilter = new PullLightingFilter(pd.getLightPos().getUnitVector());
            lightingFilter.setPredecessor(colorFilter);
            pipelineOutput = lightingFilter;
        } else {
            pipelineOutput = colorFilter;
        }

        PullProjectionFilter projection = new PullProjectionFilter(pd.getProjTransform());
        projection.setPredecessor(pipelineOutput);

        PullPerspectiveDivisionFilter perspective = new PullPerspectiveDivisionFilter();
        perspective.setPredecessor(projection);

        PullViewportTransformFilter viewport = new PullViewportTransformFilter(pd.getViewportTransform());
        viewport.setPredecessor(perspective);

        PullRenderer renderer = new PullRenderer(
                pd.getGraphicsContext(),
                pd.getRenderingMode(),
                viewport
        );



        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {
                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRotation = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);

                transformFilter.updateRotationMatrix(newRotation);

                modelSource.reset();
                depthSortingFilter.reset();

                modelSource.run(model);
                renderer.render();
            }

        };
    }
}