package edu.iis.mto.oven;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    Oven oven;

    @Mock
    Fan fan;

    @Mock
    HeatingModule module;

    @BeforeEach
    void setUp(){
        oven = new Oven(module, fan);
    }

    @Test
    void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    void normalRunHeaterTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                        .withStageTime(100)
                        .withHeat(HeatType.HEATER)
                        .withTargetTemp(300)
                        .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunGrillTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.GRILL)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunThermoTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunWithTwoStagesTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.HEATER)
                .withTargetTemp(300)
                .build();
        ProgramStage stage2 = ProgramStage.builder()
                .withStageTime(250)
                .withHeat(HeatType.GRILL)
                .withTargetTemp(200)
                .build();
        stages.add(stage1);
        stages.add(stage2);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(0)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

}
